let baseURL = 'http://localhost:9090/blob/';
let updateInfo = baseURL + 'user/updateInfo';
let userInfo = baseURL + 'user/info';
let otherInfo = baseURL + 'user/other/info';
let userPost = baseURL + 'forum/user/unapproval/post';
let likeCollectPost = baseURL + 'forum/my/like/collect/post';
let socialC = baseURL+'social/concern';
let user = null;
let sortLike = 0;
let sortCollect = 1;
let sortTime = 2;
let pageNumMy = 1;
let pageNumLike = 1;
let pageNumCollect = 1;
let pageSize = 16;
let isLoadingMyPosts = false;
let isLoadingLikePosts = false;
let isLoadingCollectPosts = false;
let authorization = localStorage.getItem('authorization');
window.onload = async function () {
    fetch(userInfo, {
        method: 'get',
        credentials: 'include',
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
                var otherId = getUserIdFromUrl();
                if(otherId){
                    if(user.userId!=otherId){
                        document.getElementById('edit-profile').style.display = 'none';
                    }
                    var formData = new FormData();
                    formData.append('otherId',otherId);
                    fetch(otherInfo,{
                        method:'post',
                        body:formData
                    }).then(response=>response.json())
                    .then(res=>{
                        if(res.success){
                            var other = res.data.userInfoDto;
                            nameList[1].innerHTML = "用户名：" + other.name;
                            uidList[1].innerHTML = "UID:" + other.userId;
                            document.getElementById('followers').innerText = "关注数:" + other.concern;
                            document.getElementById('fans').innerText = "粉丝数:" + other.followers;
                            document.getElementById('posts').innerText = "帖子数:" + other.post;
                            document.getElementById('likes').innerText = "喜欢数:" + other.love;
                            document.getElementById('collects').innerText = "收藏数:" + other.collect;
                            document.getElementsByClassName('avatar')[0].src = other.photo;  // 更改新的图片元素的src，而不是原有的头像图片
                        }else{
                            alert(res.message);
                        }
                    }).catch(error=>{
                        console.error(error);
                    })
                }else{
                    nameList[1].innerHTML = "用户名：" + user.name;
                    uidList[1].innerHTML = "UID:" + user.userId;
                    document.getElementById('followers').innerText = "关注数:" + user.concern;
                    document.getElementById('fans').innerText = "粉丝数:" + user.followers;
                    document.getElementById('posts').innerText = "帖子数:" + user.post;
                    document.getElementById('likes').innerText = "喜欢数:" + user.love;
                    document.getElementById('collects').innerText = "收藏数:" + user.collect;
                    document.getElementById('avatar').src = user.photo;
                    document.getElementsByClassName('avatar')[0].src = user.photo;  // 更改新的图片元素的src，而不是原有的头像图片
                }
                var myPostsButton = document.querySelector('.tablink');
                openPage('MyPosts', myPostsButton,sortTime,getUserIdFromUrl(),userPost);
            } else {
                alert(data.message);
            }
        }).catch(error => {
            console.error('Error:', error);
        });
        
}
window.addEventListener('scroll', async function() {
    if ((window.innerHeight + window.scrollY) >= document.body.offsetHeight) {
        if (!isLoadingMyPosts && document.getElementById('MyPosts').style.display === 'block') {
            isLoadingMyPosts = true;
            pageNumMy++;
            let newPosts = await getPost(sortTime, getUserIdFromUrl(), userPost, null, pageNumMy);
            if (newPosts.list.length > 0) {
                showUserPost(newPosts);
            } else {
                pageNumMy--;
            }
            isLoadingMyPosts = false;
        } else if (!isLoadingLikePosts && document.getElementById('MyLikes').style.display === 'block') {
            isLoadingLikePosts = true;
            pageNumLike++;
            let newPosts = await getPost(sortLike, getUserIdFromUrl(), likeCollectPost, 0, pageNumLike);
            if (newPosts.list.length > 0) {
                showLikePost(newPosts);
            } else {
                pageNumLike--;
            }
            isLoadingLikePosts = false;
        } else if (!isLoadingCollectPosts && document.getElementById('MyCollects').style.display === 'block') {
            isLoadingCollectPosts = true;
            pageNumCollect++;
            let newPosts = await getPost(sortCollect, getUserIdFromUrl(), likeCollectPost, 1, pageNumCollect);
            if (newPosts.list.length > 0) {
                showCollectPost(newPosts);
            } else {
                pageNumCollect--;
            }
            isLoadingCollectPosts = false;
        }
    }
});

function getUserIdFromUrl() {
    var query = window.location.search.substring(1);
    var vars = query.split("=");
    if (vars[0] === "userId" && vars[1]) {
        return vars[1];
    } else {
        return user.userId;
    }
}

async function openPage(pageName, elmnt,sort,userId,url,status) {
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
     var parent = document.getElementById(pageName);
     parent.style.display = "block";
     for(var i = 1;i<=4;i++){
        var imgBox =document.getElementById('imgBox'+pageName+i);
        if(imgBox==null){
            var child = document.createElement('img-box');
            parent.appendChild(child);
            imgBox=child;
        }
        imgBox.innerHTML='';
     }

    // Add the specific color to the button used to open the tab content
    elmnt.style.backgroundColor = "#777";
    var data = null;
    var pageNum = 0;
    if(sort!=null){
        pageNum = pageNumMy;
        data = await getPost(sort,userId,url,status,pageNum);
        console.log(data);
        showUserPost(data);
    }else{
        if(status==0){
            pageNum = pageNumLike;
            data = await getPost(sort,userId,url,status,pageNum);
            console.log(data);
            showLikePost(data);
        }else{
            pageNum = pageNumCollect;
            data = await getPost(sort,userId,url,status,pageNum);
            console.log(data);
            showCollectPost(data);
        }
    }
    
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
        credentials: 'include',
        headers: {
            'Authorization': authorization
        }
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
async function getPost(sort, otherId, url,status,pageNum) {
    var formData = new FormData();
    if(sort!=null){
        formData.append('sort', sort);
    }
    if(status!=null){
        formData.append('status', status);
    }
    formData.append('pageNum', pageNum);
    formData.append('pageSize', pageSize);
    if (otherId != null) {
        formData.append('otherId', otherId);
    }
    try {
        let response = await fetch(url, {
            method: "post",
            body: formData,
            headers: {
                'Authorization': authorization
            }
        });
        let res = await response.json();
        if (res.success) {
            return res.data;
        } else {
            alert(res.message);
            console.log(res.message);
        }
    } catch (err) {
        console.error(err);
    }
}
window.linkToOne = function(element,id){
    console.log('click');
    element.className = ('loading followlink');
    element.innerHTML = "<span>.</span><span>.</span><span>.</span>";
    var formData = new FormData();
    formData.append('otherId',id);
    try{
        fetch(socialC,{
            method:'post',
            body:formData,
            headers: {
                'Authorization': authorization
            }
        }).then(response=>response.json())
        .then(res=>{
            if(res.success){
                element.onclick = null;
                element.className = "link useLinks";
                element.innerHTML = "你的关注";
            }else{
                element.className = 'link followlink';
                element.innerHTML = "+";
                alert('网络错误')
            }
        }).catch(err=>{
            console.error(err);
        })
    }catch(err){
        alert(err);
    }
}
function showUserPost(res) {
    console.log(res);
    let posts = res;
    if(posts.length==0){
        showEmptyInfo('imageListMyPosts');
        return;
    }
    if (posts.length > 0) {
        var firstPost = posts[0];
        var otherInfoDto = firstPost.otherInfoDto;
    }
    var photo = otherInfoDto.userInfoDto.photo;
    var name = otherInfoDto.userInfoDto.name;
    var status = otherInfoDto.status;
    var otherId = otherInfoDto.userInfoDto.userId;
    posts.forEach((item, index) => {
        var i = index % 4 + 1
        var target = document.getElementById('imgBoxMyPosts' + i);
        let cont = "<information-box><a href='postDetail.html?postId=" + item.postId +
            "'><img src='" + item.cover + "'>" +
            "<div class='titleBelowImg'>" + item.title + "</div></a><tag>" +
            item.tag.split("|").map(function (item) {
                return `<a href="multiPersonSquare.html?tag=${item}">${item}</a>`
            }).join("") + "</tag><div class='userBelowImg'><a class='titleBelow_a'href='center.html?userId=" + otherId +
            "'><img class='iconOfUse'src=" + photo +
            "><div style='text-indent:0.55em;'>" + name + "</div></a>" + Status(status,otherId) + "</div></information-box>"
        // console.log(cont);
        if (cont !== null) {
            target.innerHTML += cont;
        }
    })

}
function showLikePost(res) {
    console.log(res);
    let posts = res;
    if(posts.length==0){
        showEmptyInfo('imageListMyLikes');
        return;
    }
    posts.forEach((item, index) => {
        var i = index % 4 + 1
        var target = document.getElementById('imgBoxMyLikes' + i);
        let cont = "<information-box><a href='postDetail.html?postId=" + item.postId +
            "'><img src='" + item.cover + "'>" +
            "<div class='titleBelowImg'>" + item.title + "</div></a><tag>" +
            item.tag.split("|").map(function (item) {
                return `<a href="multiPersonSquare.html?tag=${item}">${item}</a>`
            }).join("") + "</tag><div class='userBelowImg'><a class='titleBelow_a'href='center.html?userId=" + item.otherInfoDto.userInfoDto.userId +
            "'><img class='iconOfUse'src=" + item.otherInfoDto.userInfoDto.photo +
            "><div style='text-indent:0.55em;'>" + item.otherInfoDto.userInfoDto.name + "</div></a>" + Status(item.otherInfoDto.status,item.otherInfoDto.userInfoDto.userId) + "</div></information-box>"
        // console.log(cont);
        if (cont !== null) {
            target.innerHTML += cont;
        }
    });

}
function showCollectPost(res) {
    console.log(res);
    let posts = res;
    if(posts.length==0){
        showEmptyInfo('imageListMyCollects');
        return;
    }
    posts.forEach((item, index) => {
        var i = index % 4 + 1
        var target = document.getElementById('imgBoxMyCollects' + i);
        let cont = "<information-box><a href='postDetail.html?postId=" + item.postId +
            "'><img src='" + item.cover + "'>" +
            "<div class='titleBelowImg'>" + item.title + "</div></a><tag>" +
            item.tag.split("|").map(function (item) {
                return `<a href="multiPersonSquare.html?tag=${item}">${item}</a>`
            }).join("") + "</tag><div class='userBelowImg'><a class='titleBelow_a'href='center.html?userId=" + item.otherInfoDto.userInfoDto.userId +
            "'><img class='iconOfUse'src=" + item.otherInfoDto.userInfoDto.photo +
            "><div style='text-indent:0.55em;'>" + item.otherInfoDto.userInfoDto.name + "</div></a>" + Status(item.otherInfoDto.status,item.otherInfoDto.userInfoDto.userId) + "</div></information-box>"
        // console.log(cont);
        if (cont !== null) {
            target.innerHTML += cont;
        }
    })

}
function Status(status,otherId) {
    switch (status) {
        case (0): return "<div class='link followlink' onclick='linkToOne(this,\"" + otherId + "\")'>+</div>";
        case (1): return "<div class='link useLinks'>你的关注</div>";
        case (2): return "<div class='link followlink'>+</div>";
        case (3): return "<div class='link useLinks'>互关好友</div>";
        case (4): return "<div class='link linksMe'>我</div>";
    }
}
function showEmptyInfo(id) {
    console.log(id)
    var emptyTarget = document.getElementById(id);
    var cont = "<div style='display:flex;align-items:center;'>" +
        "<div><img style='width:10em' src='http://121.40.154.188:8080/image/emptyPage.png'></div>" +
        "<div><div id='empty_txt'>空&nbsp;空&nbsp;如&nbsp;也</div>" +
        "<div style='font-size:20px;color:#dfdfdf'>这里什么都没有……</div></div>" +
        "</div>"
    emptyTarget.innerHTML = cont;
}