var stompClient = null;

function setConnected(connected) {
    $("#enter_btn").prop("disabled", connected);
    $("#nickname").prop("disabled", connected);
    $("#leave_btn").prop("disabled", !connected);
}

function connect() {
	if ($.trim($("#nickname").val()) == '') {
		alert("nickname is empty!");
		return;
	}
	
    var socket = new SockJS('/shallwetalk');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/show', function (e) {
        	var msg = JSON.parse(e.body);
            showMsg(msg.time, msg.name, msg.content);
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
    $("#input").val("");
}

function showMsg(time, name, message) {
    $("#msg").append("<tr><td>" + time + "</td><td>" + name + "</td><td>" + message + "</td></tr>");
    $("#scroll_div").scrollTop($("#scroll_div").scrollTop() + 100);
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#enter_btn" ).click(function() { connect(); });
    $( "#leave_btn" ).click(function() { disconnect(); });
    $( "#send_btn" ).click(function() { sendMsg(); });
});