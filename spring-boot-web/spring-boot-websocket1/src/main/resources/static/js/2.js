const host = "http://127.0.0.1:8090";
let stompClient = null;
const socket_endpoint = "/chat";
const broker_topic = "/topic"
const app_prefix = "/app"

function setView(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
        $("#chat").show();
    } else {
        $("#conversation").hide();
        $("#chat").hide();
    }
    $("#greetings").html("");
}

function connect() {
    if (!$("#name").val()) {
        render("输入用户名");
        return;
    }

    const s = host + socket_endpoint;
    const d = broker_topic + '/greetings';

    const socket = new SockJS(s);
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected:' + frame);
        stompClient.subscribe(d, function (greeting) {
            render(JSON.parse(greeting.body));
        });
    });
    setView(true);
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setView(false);
    console.log("Disconnected");
}

function sendMessage() {
    stompClient.send(app_prefix + "/hello", {},
        JSON.stringify({'name': $("#name").val(), 'message': $("#message").val()}));
}

function render(message) {
    $("#greetings").append(
        "<div>" + message.name + ":" + message.message + "</div>"
    );
}

$(function () {
    $("#connect").click(function () {
        connect();
    });
    $("#disconnect").click(function () {
        disconnect();
    });
    $("#send").click(function () {
        sendMessage();
    });
});