//gateway网关的地址
const host = "http://127.0.0.1:8090";
let stompClient = null;
const socket_endpoint = "/chat";
const broker_topic = "/topic"
const app_prefix = "/app"
let token = new Date().getTime()

function setView(connected) {
    document.getElementById('connect').disabled = connected;
    document.getElementById('disconnect').disabled = !connected;
    document.getElementById('chat').style.visibility = connected ? 'visible' : 'hidden';
    if (connected) {
        $("#chat").show();
    } else {
        $("#chat").hide();
    }
    $('#response').html();
}

function connect() {
    // 保持name唯一作为token
    token = $('#name').val();
    let s = host + socket_endpoint + '?token=' + token
    // 广播订阅用
    let d = broker_topic + '/sendTopic'
    // 点对点订阅用
    // d = '/user/queue/sendUser'

    const socket = new SockJS(s);
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected:' + frame);
        stompClient.subscribe(d, function (response) {
            render(response.body);
        });
    });
    setView(true);
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    setView(false);
    console.log("Disconnected");
}

function render(message) {
    $("#response").append(
        "<div>" + message + "</div>"
    );
}

function sendMessage() {
    const name = $('#name').val();
    const message = $('#message').val();
    sendMessage1(message)
}

function sendMessage1(message) {
    // 测试发送消息到服务端
    stompClient.send(app_prefix + "/sendServer", {}, message);
}

function sendMessage2(message) {
    // 需订阅 /topic/sendTopic
    stompClient.send(app_prefix + "/sendTopic", {}, message)
}

function sendMessage3(message) {
    // 需订阅 /topic/sendTopic
    stompClient.send(app_prefix + "/sendAllUser", {}, message);
}

function sendMessage4(message) {
    // 需订阅 /user/queue/sendUser
    stompClient.send(app_prefix + "/sendUser", {}, token);
}

function sendMessage5(message) {
    // 需订阅 /user/queue/sendUser
    stompClient.send(app_prefix + "/sendMyUser", {}, JSON.stringify({token: token, message: message}));
}
