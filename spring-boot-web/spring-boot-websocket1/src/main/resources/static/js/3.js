const host = "http://127.0.0.1:8090";
const socket_endpoint = "/chat";
const app_prefix = "/app"
const broker_topic = "/topic"
const user_prefix = "/user"
let stompClient = null;
let token = null

function connect() {
    if (token === null) {
        token = $("#name").val()
        if (!token) {
            alert("输入用户名")
            return;
        }
    }

    const socket = new SockJS(host + socket_endpoint + '?token=' + token);
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log("连接成功")
    });
    setView(true)
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect(function () {
            console.log("断开连接");
        });
    }
    setView(false)
}

function setView(connected) {
    $("#id_connect").prop("disabled", connected);
    $("#id_disconnect").prop("disabled", !connected);
    if (connected) {
        $("#row2").show();
    } else {
        $("#row2").hide();
    }
}

// 设置订阅消息的请求地址
let subscribe_url = "";
let subscribe = null;

function subscribeSocket() {
    if (subscribe !== null) {
        subscribe.unsubscribe()
    }
    subscribe_url = $("#select_topics1").val() + $("#subscribe").val();
    alert("设置订阅地址为：" + subscribe_url);
    subscribe = stompClient.subscribe(subscribe_url, function (response) {
        console.log(response)
        const msg = JSON.parse(response.body);
        $("#information").append("<tr><td>" + msg.content + "</td></tr>");
    });
}

function sendMessage() {
    sendMessage0()
    // sendMessage1()
}

// 广播
function sendMessage0() {
    const sub_url = $("#select_topics2").val() + $("#topic").val()
    const message = JSON.stringify({
        'content': $("#content").val(),
        'destination': sub_url
    })
    stompClient.send(app_prefix + "/2topic", {}, message)
}

// 点对点
function sendMessage1() {
    const toName = $("#toName").val()
    const sub_url = $("#select_topics2").val() + $("#topic").val()
    const message = JSON.stringify({
        'content': $("#content").val(),
        'destination': sub_url,
        'from': {'token': token, 'name': token},
        'to': {'token': toName, 'name': toName},
        'timestamp': new Date().getTime()
    })
    stompClient.send(app_prefix + "/2user", {}, message)
}