let socket;
let heartbeatInterval = 30000; // 心跳包发送间隔时间
let authHeader = localStorage.getItem('authorization'); // 获取授权头

function connectWebSocket() {
    const url = `ws://localhost:9091/ws`;

    socket = new WebSocket(url, [authHeader]);
    socket.addEventListener('open', (event) => {
        console.log('ws连接已建立');
        startHeartbeat();
    });

    socket.onmessage = function(event) {
        console.log('收到消息：', event.data);
    };

    socket.onclose = function() {
        console.log('ws连接已关闭');
        // 尝试重新连接
        setTimeout(connectWebSocket, 5000);
    };

    socket.onerror = function(error) {
        console.log('ws连接错误:', error);
        console.log(socket)
        socket.close();
    };
}

function startHeartbeat() {
    setInterval(function() {
        if (socket.readyState === WebSocket.OPEN) {
            socket.send("heartBeat");
        }
    }, heartbeatInterval);
}
// 初始化WebSocket连接
connectWebSocket();
