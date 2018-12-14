var stompClient = null;

function setConnected(connected) {
    $("#enter_btn").prop("disabled", connected);
    $("#leave_btn").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#msg").html("");
}

function connect() {
    var socket = new SockJS('/shallwetalk');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/show', function (e) {
        	var msg = JSON.parse(e.body);
            showMsg(msg.name, msg.content);
        });
        sendName();
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
	stompClient.send("/app/enter", {}, JSON.stringify({'name': $("#nickname").val()}));
}

function sendMsg() {
    stompClient.send("/app/send", {}, JSON.stringify({'content': $("#input").val()}));
}

function showMsg(name, message) {
    $("#msg").append("<tr><td>" + name + "</td><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#enter_btn" ).click(function() { connect(); });
    $( "#leave_btn" ).click(function() { disconnect(); });
    $( "#send_btn" ).click(function() { sendMsg(); });
});