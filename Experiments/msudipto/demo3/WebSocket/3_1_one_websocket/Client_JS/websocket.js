
var ws;

function connect() {
    var username = document.getElementById("username").value;
    var token = document.getElementById("token").value;
    var wsserver = document.getElementById("wsserver").value;
    var url = wsserver + username + "/" + token;

    ws = new WebSocket(url);

    ws.onopen = function(event) { // Connection opened
        var log = document.getElementById("log");
        log.innerHTML += "Connected to " + event.currentTarget.url + "\n";
    };

    ws.onmessage = function(event) { // Message received
        console.log(event.data);
        var log = document.getElementById("log");
        log.innerHTML += "Server: " + event.data + "\n";
    };

    ws.onerror = function(event) { // Connection error
        var log = document.getElementById("log");
        log.innerHTML += "Error: " + event.message + "\n";
    };

    ws.onclose = function(event) { // Connection closed
        var log = document.getElementById("log");
        log.innerHTML += "Disconnected from server.\n";
    };
}

function send() {
    var content = document.getElementById("msg").value;
    if (ws && ws.readyState === WebSocket.OPEN) {
        ws.send(content);
        document.getElementById("msg").value = ""; // Clear input after sending
    } else {
        var log = document.getElementById("log");
        log.innerHTML += "Unable to send message. Not connected.\n";
    }
}
