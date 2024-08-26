let baseURL = 'http://localhost:9090/blob/';
let userInfo = baseURL + 'user/info';
let myGroup = baseURL + 'group/select/my';
let groupList = baseURL+'group/select/list';
let groupMessage = baseURL + 'group/select/comment';
let sendGroupMessage = baseURL + 'group/chat';
let createGroup = baseURL + 'group/create';
let addGroup = baseURL + 'group/add';
let exitGroup = baseURL+'group/exit';
let user = null;
let authorization = localStorage.getItem('authorization');
let post = 'post';
let get = 'get';
let pageSize = 10;
let pageNum = 1;
let activeGroupId = null;
let username = null;
let avatar = null;
let userId = null;
window.addEventListener('load', async function() {
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
                username = user.name;
                var uidList = document.getElementsByClassName('uid');
                uidList[0].innerHTML = "UID:" + user.userId;
                userId = user.userId;
                localStorage.setItem('userId', userId);
                var avatarList = document.getElementsByClassName('avatar');
                avatarList[0].src = user.photo;
                avatar = user.photo;
            }
        }).catch(error => {
        console.error(error);
    });
    showMyGroup();
    showGroupList();
});

async function setActiveGroup(element) {
    // 移除所有群组的active状态
    var groups = document.querySelectorAll('.group');
    groups.forEach(function (group) {
        group.classList.remove('active');
    });
    activeGroupId = element.getAttribute('data-group-id');
    document.getElementById('groupName').innerText = element.getAttribute('data-group-name');
    document.getElementById('groupId').innerText = "GID:" + element.getAttribute('data-group-id');
    var success = await add2Group(activeGroupId);
    if (success) {
        showHistoryMessage(activeGroupId);
        document.getElementById('messageInput').type = "text";
        document.getElementById('sendButton').style.display = "";
    }
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
async function request(url, method, formData) {
    const options = {
        method: method,
        headers: {
            'Authorization': authorization
        }
    };

    if (formData !== null) {
        options.body = formData;
    }

    const response = await fetch(url, options);
    const result = await response.json();
    return result;
}
//展示我的群组
function showMyGroup(){
    request(myGroup,post,null)
        .then(async data => {
            if (data.success) {
                var myGroup = document.getElementById("myGroup");
                myGroup.innerHTML = "";
                if (data.data) {
                    var res = data.data;
                    var group = document.createElement('div');
                    group.className = "group active";
                    group.setAttribute('data-group-id', res.id); // 存储 groupId
                    group.setAttribute('data-group-name', res.name);
                    group.addEventListener('click', function () {
                        setActiveGroup(this);
                    });
                    group.innerHTML = `
                        <img class="groupAvatar" src="${res.avatar}" alt="群组头像">
                        <div class="groupInfo">
                            <div class="groupName">${res.name}</div>
                            <div class="groupId">${res.id}</div>
                        </div>
                    `;
                    myGroup.appendChild(group);
                    var success = await add2Group(res.id);
                    if(success){
                        showHistoryMessage(res.id);
                    }
                    activeGroupId = res.id;
                } else {
                    myGroup.innerHTML = "<button id='create'>创建群组</button>";
                    document.getElementById('chatContent').innerHTML = "";
                    document.getElementById('messageInput').type = "hidden";
                    document.getElementById('sendButton').style.display = "none";
                    // 绑定创建群组按钮的点击事件
                    var btn = document.getElementById("create");
                    btn.onclick = function () {
                        var modal = document.getElementById("createGroupModal");
                        modal.style.display = "block";
                    }
                }
            } else {
                alert(data.message);
            }
        }).catch(err=>{
            console.error(err);
    })
}
function showGroupList(){
    var formData = new FormData();
    formData.append('pageSize',pageSize);
    formData.append('pageNum',pageNum);
    request(groupList,post,formData)
        .then(data=>{
            if(data.success){
                var groupList = document.getElementById("groupList");
                groupList.innerHTML = "";
                var list = data.data;
                list.forEach((item,index)=>{
                    var group = document.createElement('div');
                    group.className = "group";
                    group.setAttribute('data-group-id', item.id); // 存储 groupId
                    group.setAttribute('data-group-name', item.name);
                    group.addEventListener('click', function() {
                        setActiveGroup(this);
                    });
                    group.innerHTML = `
                        <img class="groupAvatar" src="${item.avatar}" alt="群组头像">
                        <div class="groupInfo">
                            <div class="groupName">${item.name}</div>
                            <div class="groupId">${item.id}</div>
                        </div>
                    `;
                    groupList.appendChild(group);
                    /*console.log(item,index)*/
                })
            }
        })
}
function showHistoryMessage(groupId){
    var formData = new FormData();
    formData.append('groupId',groupId);
    formData.append('pageSize',pageSize);
    formData.append('pageNum',pageNum);
    request(groupMessage,post,formData)
        .then(data=>{
            if(data.success){
                var chatContent = document.getElementById("chatContent");
                chatContent.innerHTML = "";
                var list = data.data.groupCommentDtos;
                var ownerId = data.data.ownerId;
                for(var i = list.length-1;i>=0;i--){
                    var message = document.createElement('div');
                    if(list[i].status === 0){
                        message.className = "messageContainer myContent";
                        message.innerHTML= `
                            <div class="message">
                                <div class="messageName myName">
                                    ${list[i].name}
                                </div>
                                <div class="messageContent">
                                    ${list[i].content}
                                </div>
                            </div>
                            <img class="avatarChat" src="${list[i].avatar}" alt="头像"/>
                        `;
                    }else{
                        message.className = "messageContainer otherContent";
                        if(userId && userId === ownerId){
                            message.innerHTML= `
                                <img class="avatarChat" src="${list[i].avatar}" alt="头像"/>
                                <div class="message">
                                    <div class="messageName otherName">
                                        ${list[i].name}
                                        <button class="remove" onclick="remove(${list[i].userId})">踢出</button>
                                    </div>
                                    <div class="messageContent">
                                        ${list[i].content}
                                    </div>
                                </div>
                            `;
                        }else{
                            message.innerHTML= `
                               <img class="avatarChat" src="${list[i].avatar}" alt="头像"/>
                               <div class="message">
                                  <div class="messageName otherName">
                                     ${list[i].name}
                                  </div>
                                  <div class="messageContent">
                                     ${list[i].content}
                                  </div>
                               </div>
                            `;
                        }
                        
                    }
                    chatContent.appendChild(message);
                }
                // 自动滚动到底部
                chatContent.scrollTop = chatContent.scrollHeight;
            }
        })
}
document.getElementById('sendButton').addEventListener('click', function() {
    const messageInput = document.getElementById('messageInput');
    const messageContent = messageInput.value.trim();
    if (messageContent && activeGroupId) {
        console.log(messageContent);
        console.log(activeGroupId);
        sendMessage(activeGroupId, messageContent);
        messageInput.value = ''; // 清空输入框
    }
});
function sendMessage(groupId,content){
    var formData = new FormData();
    formData.append('groupId',groupId);
    formData.append('content',content);
    request(sendGroupMessage,post,formData)
        .then(data=>{
            if(data.success){
                var chatContent = document.getElementById("chatContent");
                var message = document.createElement('div');
                message.className = "messageContainer myContent";
                message.innerHTML= `
                        <div class="message">
                            <div class="messageName myName">
                                ${username}
                            </div>
                            <div class="messageContent">
                                ${content}
                            </div>
                        </div>
                        <img class="avatarChat" src="${avatar}" alt="头像"/>
                `;
                chatContent.appendChild(message);
                // 自动滚动到底部
                chatContent.scrollTop = chatContent.scrollHeight;
            }
        })
}
/*function createGroup(){
    request(createGroup,post,null);
}*/
function remove(userId){
    
}
// 获取模态窗口元素
var modal = document.getElementById("createGroupModal");

// 获取关闭模态窗口的 <span> 元素
var span = document.getElementsByClassName("close")[0];

// 当用户点击 <span> (x)，关闭模态窗口
span.onclick = function() {
    modal.style.display = "none";
}

// 当用户点击模态窗口外部，关闭模态窗口
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}

// 处理创建群组表单提交
document.getElementById("createGroupForm").addEventListener("submit", function(event) {
    event.preventDefault();
    // 获取表单数据
    var groupName = document.getElementById("name").value;
    var groupAvatar = document.getElementById("avatar").files[0];
    var formData = new FormData();
    formData.append("name", groupName);
    formData.append("file", groupAvatar);
    request(createGroup,post,formData)
        .then(data=>{
            if(data.success){
                alert("创建成功!");
                window.location.reload();
            }
        })
    // 关闭模态窗口
    modal.style.display = "none";
});
// 预览图片功能
document.getElementById("avatar").addEventListener("change", function(event) {
    var preview = document.getElementById("preview");
    var file = event.target.files[0];
    var reader = new FileReader();

    reader.onloadend = function() {
        preview.src = reader.result;
    }

    if (file) {
        reader.readAsDataURL(file);
    } else {
        preview.src = "http://localhost:9090/blob/img/group.png";
    }
});
async function add2Group(groupId) {
    var formData = new FormData();
    formData.append("groupId", groupId);
    try {
        let data = await request(addGroup, post, formData);
        if (data.success) {
            return true;
        } else {
            alert("加入失败!");
            return false;
        }
    } catch (error) {
        console.error("请求失败:", error);
        return false;
    }
}
// 页面卸载时发送请求移除用户的 channel
window.addEventListener('beforeunload', function() {
    // 发送异步请求到后端移除用户的 channel
    request(exitGroup, post, null)
        .catch(error => {
            console.error(error);
        });
});




