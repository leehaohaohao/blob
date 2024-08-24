let baseURL = 'http://localhost:9090/blob/';
let userInfo = baseURL + 'user/info';
let user = null;
let authorization = localStorage.getItem('authorization');
window.onload = async function () {
    fetch(userInfo, {
        method: 'get',
        headers: {
            'Authorization': authorization
        }
    }).then(response => response.json())
        .then(data => {
            if (data.success) {
                user = data.data;
                var nameList = document.getElementsByClassName('name');
                nameList[0].innerHTML = "用户名：" + user.name;
                var uidList = document.getElementsByClassName('uid');
                uidList[0].innerHTML = "UID:" + user.userId;
            }
        }).catch(error=>{
        console.error(error)
        });
}
function setActiveGroup(element) {
    // 移除所有群组的active状态
    var groups = document.querySelectorAll('.group');
    groups.forEach(function(group) {
        group.classList.remove('active');
    });

    // 为点击的群组添加active状态
    element.classList.add('active');
}
document.getElementById('messageInput').addEventListener('keypress', function(event) {
    if (event.key === 'Enter') {
        event.preventDefault(); // 阻止默认的Enter键行为
        document.getElementById('sendButton').click(); // 触发发送按钮的点击事件
    }
});
document.getElementById('searchInput').addEventListener('keypress', function(event) {
    if (event.key === 'Enter') {
        event.preventDefault(); // 阻止默认的Enter键行为
        document.getElementById('searchButton').click(); // 触发发送按钮的点击事件
    }
});

