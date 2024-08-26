let socket;
let heartbeatInterval = 16000; // 心跳包发送间隔时间
let authHeader = localStorage.getItem('authorization'); // 获取授权头
let wsUserId = localStorage.getItem('userId');

function connectWebSocket() {
    // 检查是否已经存在 WebSocket 连接
    if (sessionStorage.getItem('socket')) {
        console.log('WebSocket 已经连接');
        socket = JSON.parse(sessionStorage.getItem('socket'));
        return;
    }
    const url = `ws://localhost:9091/ws`;

    socket = new WebSocket(url, [authHeader]);
    socket.addEventListener('open', (event) => {
        console.log('ws连接已建立');
        startHeartbeat();
        // 将 WebSocket 连接存储在 sessionStorage 中
        sessionStorage.setItem('socket', JSON.stringify(socket));
    });

    socket.onmessage = function(event) {
        console.log('收到消息：', event.data);
        var mes = JSON.parse(event.data);
        console.log(mes);

        var chatContent = document.getElementById('chatContent');
        if (chatContent) {
            var message = document.createElement('div');
            message.className = "messageContainer otherContent";
            message.innerHTML= `
                <img class="avatarChat" src="${mes.avatar}" alt="头像"/>
                <div class="message">
                    <div class="messageName otherName">            
                        ${mes.name}            
                        ${mes.ownerId === wsUserId ? `<button class="remove" onclick="remove(${mes.userId})">踢出</button>` : ''}            
                    </div>            
                    <div class="messageContent">            
                        ${mes.content}           
                    </div>            
                </div>                                    
            `;
            chatContent.appendChild(message);
            // 自动滚动到底部
            chatContent.scrollTop = chatContent.scrollHeight;
        }
    };

    socket.onclose = function() {
        console.log('ws连接已关闭');
        // 尝试重新连接
        sessionStorage.removeItem('socket');
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

// 页面加载时连接 WebSocket
window.addEventListener('load', function() {
    connectWebSocket();
});
window.addEventListener('beforeunload', function(){
    if(socket){
        socket.close();
        console.log('关闭socket');
    }
})
