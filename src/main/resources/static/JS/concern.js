let baseURL = 'http://localhost:9090/blob/';
let updateInfo = baseURL + 'user/updateInfo';
let userInfo = baseURL + 'user/info';
let otherInfo = baseURL + 'user/other/info';
let socialC = baseURL + 'social/concern';
let concernFollower = baseURL + 'social/get/concern/follower';
let user = null;
let pageNumConcern = 1;
let pageNumFollower = 1;
let pageSize = 16;
let authorization = localStorage.getItem('authorization');
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
                var uidList = document.getElementsByClassName('uid');
                uidList[0].innerHTML = "UID:" + user.userId;
                var avatarList = document.getElementsByClassName('avatar');
                avatarList[0].src = user.photo;
                var otherId = getUserIdFromUrl();
                if (otherId) {
                    if (user.userId != otherId) {
                        document.getElementById('edit-profile').style.display = 'none';
                    }
                    var formData = new FormData();
                    formData.append('otherId', otherId);
                    fetch(otherInfo, {
                        method: 'post',
                        body: formData,
                        headers: {
                            'Authorization': authorization
                        }
                    }).then(response => response.json())
                        .then(res => {
                            if (res.success) {
                                var other = res.data.userInfoDto;
                                nameList[1].innerHTML = "用户名：" + other.name;
                                uidList[1].innerHTML = "UID:" + other.userId;
                                document.getElementById('followers').innerText = "关注数:" + other.concern;
                                document.getElementById('fans').innerText = "粉丝数:" + other.followers;
                                document.getElementById('posts').innerText = "帖子数:" + other.post;
                                document.getElementById('likes').innerText = "喜欢数:" + other.love;
                                document.getElementById('collects').innerText = "收藏数:" + other.collect;
                                document.getElementById('avatar').src = other.photo;  // 更改新的图片元素的src，而不是原有的头像图片
                            } else {
                                alert(res.message);
                            }
                        }).catch(error => {
                        console.error(error);
                    })
                } else {
                    nameList[1].innerHTML = "用户名：" + user.name;
                    uidList[1].innerHTML = "UID:" + user.userId;
                    document.getElementById('followers').innerText = "关注数:" + user.concern;
                    document.getElementById('fans').innerText = "粉丝数:" + user.followers;
                    document.getElementById('posts').innerText = "帖子数:" + user.post;
                    document.getElementById('likes').innerText = "喜欢数:" + user.love;
                    document.getElementById('collects').innerText = "收藏数:" + user.collect;
                    document.getElementById('avatar').src = user.photo;
                }
                var myPostsButton = document.querySelector('.tablink');
                openPage('concernList', myPostsButton, 0, getUserIdFromUrl());
            } else {
                alert(data.message);
            }
        }).catch(error => {
        console.error('Error:', error);
    });
});
async function getConcernFollowerInfo(status, otherId, pageNum) {
    try {
        var jsonData = {
            "page": {
                    "pageNum": pageNum,
                    "pageSize": pageSize
                },
                "status": status,
                "otherId": otherId
        };
        var jsonString = JSON.stringify(jsonData);
        let response = await fetch(concernFollower, {
            method: 'post',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': authorization
            },
            body: jsonString,
        });
        let res = await response.json();
        if (res.success) {
            return res.data;
        } else {
            alert(res.message);
        }
    } catch (error) {
        console.error(error);
    }
}
function showInfo(res,id) {
    if (res.length == 0) {
        showEmptyInfo(id);
    } else {
        res.forEach(item => {
            var parent = document.getElementById(id);
            var user = item.userInfoDto;
            var status = item.status;

            var infoBoxHTML = `
        <div class="info_box">
            <img src="${user.photo}" class="info_avatar" alt="头像"/>
            <div class="name_id" onclick="window.location.href='center.html?userId=${user.userId}'">
                <div class="username">
                    <p>用户名: ${user.name}</p>
                </div>
                <div class="userid">
                    <p>UID: ${user.userId}</p>
                </div>
            </div>
            <div class="num_info">
                <div class="follower">
                    <p>关注数: ${user.concern}</p>
                </div>
                <div class="fan">
                    <p>粉丝数: ${user.followers}</p>
                </div>
            </div>
            <div class="concern-box">
                ${Status(status, user.userId)}
            </div>
        </div>
    `;
            parent.insertAdjacentHTML('beforeend', infoBoxHTML);
        });
    }
}
function Status(status, otherId) {
    switch (status) {
        case (0): return `<div class='concern' onclick='linkToOne(this, "${otherId}")'>关注</div>`;
        case (1): return "<p style='font-weight: bold; color: rgb(95, 121, 158); font-size: 15px; margin-left: 20px;'>我的关注</p>";
        case (2): return `<div class='concern' onclick='linkToOne(this, "${otherId}")'>关注</div>`;
        case (3): return "<p style='font-weight: bold; color: rgb(95, 121, 158); font-size: 15px; margin-left: 20px;'>我的朋友</p>";
        case (4): return "<p style='font-weight: bold; color: rgb(95, 121, 158); font-size: 15px; margin-left: 20px;'>我</p>";
    }
}
function linkToOne(element,otherId){
    var formData = new FormData();
    formData.append('otherId',otherId);
    formData.append('userId',user.userId);
    fetch(socialC,{
        method:'post',
        body:formData,
        headers: {
            'Authorization': authorization
        }
    }).then(response=>response.json())
    .then(res=>{
        if(res.success){
            var realStatus = res.data.status;
            element.outerHTML = Status(realStatus, otherId); 
        }else{
            alert(res.message);
        }
    }).catch(error=>{
        console.log(error);
    })
}
function getUserIdFromUrl() {
    var query = window.location.search.substring(1);
    var vars = query.split("=");
    if (vars[0] === "userId" && vars[1]) {
        return vars[1];
    } else {
        return user.userId;
    }
}

async function openPage(pageName, elmnt, status, otherId) {
    // Hide all elements with class="tabcontent" by default
    var i, tabcontent, tablinks;
    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }

    // Remove the background color of all tablinks/buttons
    tablinks = document.getElementsByClassName("tablink");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].style.backgroundColor = "";
    }

    // Show the specific tab content
    document.getElementById(pageName).style.display = "flex";
    document.getElementById(pageName).innerHTML='';
    // Add the specific color to the button used to open the tab content
    elmnt.style.backgroundColor = "#777";
    var data = null;
    var pageNum = 0;
    if (status == 0) {
        pageNum = pageNumConcern;
    } else {
        pageNum = pageNumFollower;
    }
    console.log(status+"==="+otherId+"====="+pageNum);
    data = await getConcernFollowerInfo(status,otherId,pageNum);
    console.log(data);
    showInfo(data,pageName);
}
// 获取模态窗口
var modal = document.getElementById("myModal");

// 获取 <span> 元素，设置关闭模态窗口的按钮
var span = document.getElementsByClassName("close")[0];

// 当用户点击按钮，打开模态窗口
function openModal() {
    modal.style.display = "block";
    document.getElementById('name').value = user.name;
    document.getElementById('telephone').value = user.telephone;
    document.getElementById('new-profile-pic').src = user.photo;  // 更改新的图片元素的src，而不是原有的头像图片
    document.getElementById(user.gender === 0 ? 'male' : 'female').checked = true;
}

// 当用户点击 <span> (x), 关闭模态窗口
span.onclick = function () {
    modal.style.display = "none";
}

// 在用户点击任何地方以外的地方关闭模态窗口
window.onclick = function (event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}

// 处理表单提交
document.getElementById('editForm').addEventListener('submit', function (event) {
    event.preventDefault();

    // 这里添加发送 POST 请求的代码
    var formData = new FormData(event.target);
    fetch(updateInfo, {
        method: 'POST',
        body: formData,
        headers: {
            'Authorization': authorization
        },
    }).then(response => response.json())
        .then(data => {
            if (data.success) {
                window.location.href = 'center.html';
            } else {
                alert(data.message);
            }
        }).catch(error => {
            console.error(error);
        })
});

// 当用户选择新的图片时，立即在页面上显示新的图片
document.getElementById('img').addEventListener('change', function (event) {
    var reader = new FileReader();
    reader.onload = function (e) {
        document.getElementById('new-profile-pic').src = e.target.result;
    };
    reader.readAsDataURL(event.target.files[0]);
});
function showEmptyInfo(id) {
    console.log(id)
    var emptyTarget = document.getElementById(id);
    var cont = "<div style='display:flex;align-items:center;'>" +
        "<div><img style='width:10em' src='http://localhost:9090/blob/img/emptyPage.png'></div>" +
        "<div><div id='empty_txt'>空&nbsp;空&nbsp;如&nbsp;也</div>" +
        "<div style='font-size:20px;color:#dfdfdf'>这里什么都没有……</div></div>" +
        "</div>"
    emptyTarget.innerHTML = cont;
}