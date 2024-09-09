let socket;
let heartbeatInterval = 70000; // 心跳包发送间隔时间
let authHeader = localStorage.getItem('authorization'); // 获取授权头
let wsUserId = localStorage.getItem('userId');

function connectWebSocket() {
    const url = `ws://localhost:9091/ws`;

    socket = new WebSocket(url, [authHeader]);
    socket.addEventListener('open', (event) => {
        console.log('ws连接已建立');
        startHeartbeat();
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
                        ${mes.ownerId === wsUserId ? `<button class="remove" onclick="remove('${mes.userId}')">踢出</button>` : ''}            
                    </div>            
                    <div class="messageContent">            
                        ${mes.content}           
                    </div>            
                </div>                                    
            `;
            chatContent.appendChild(message);
            // 判断是否滚动到底部
            autoScrollToBottom(chatContent);
        }
    };

    socket.onclose = function() {
        console.log('ws连接已关闭');
        // 尝试重新连接
        setTimeout(connectWebSocket, 5000);
    };

    socket.onerror = function(error) {
        console.log('ws连接错误:', error);
        socket.close();
    };
}
function autoScrollToBottom(chatContent) {
    const threshold = 0.05; // 5%
    const distanceToBottom = chatContent.scrollHeight - chatContent.scrollTop - chatContent.clientHeight;
    const isNearBottom = distanceToBottom / chatContent.scrollHeight <= threshold;
    if (isNearBottom) {
        chatContent.scrollTop = chatContent.scrollHeight;
    }
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
