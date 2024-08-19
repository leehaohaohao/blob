let baseURL = 'http://localhost:9090/blob/';
let numUrl = baseURL + 'back/num';
let userInfo = baseURL + 'user/info';
let hotPost = baseURL + 'back/hot/post';
let hotTag = baseURL + 'back/hot/tag';
let alertShown = false; // 全局变量，跟踪是否已经显示过提示框

window.onload = function() {
    fetch(numUrl, {
        method: 'get'
    }).then(response => response.json())
    .then(data => {
        if (data.success) {
            document.getElementById("user_num").innerText = data.data.userNum;
            document.getElementById("uuser_num").innerText = data.data.uuserNum;
            document.getElementById("post_num").innerText = data.data.postNum;
            document.getElementById("upost_num").innerText = data.data.upostNum;
            document.getElementById("tag_num").innerText = data.data.tagNum;
        } else {
            handleAlert(data.message);
        }
    }).catch(error => {
        console.error(error);
    });

    fetch(userInfo, {
        method: 'get'
    }).then(res => res.json())
    .then(data => {
        if (data.success) {
            document.getElementById('username').innerText = data.data.name;
        } else {
            handleAlert(data.message);
        }
    }).catch(error => {
        console.error(error);
    });

    var formData = new FormData();
    formData.append("pageNum", 1);
    formData.append("pageSize", 6);

    fetch(hotPost, {
        method: 'post',
        body: formData
    }).then(res => res.json())
    .then(data => {
        if (data.success) {
            var list = data.data;
            for (var i = 0; i < list.length; i++) {
                var container = document.getElementById("r-container");
                var post = document.createElement("div");
                post.className = "post";
                var a = document.createElement("a");
                a.href = "postDetail.html?postId=" + list[i].postId;
                a.innerText = list[i].title;
                post.appendChild(a);
                container.appendChild(post);
            }
        } else {
            handleAlert(data.message);
        }
    }).catch(error => {
        console.error(error);
    });

    fetch(hotTag, {
        method: 'post',
        body: formData
    }).then(res => res.json())
    .then(data => {
        if (data.success) {
            var list = data.data;
            for (var i = 0; i < list.length; i++) {
                var container = document.getElementById("l-container");
                var text = document.createElement("div");
                text.className = "text";
                var a = document.createElement("a");
                a.href = "multiPersonSquare.html?tag=" + list[i].tag;
                a.innerText = list[i].tag;
                text.appendChild(a);
                container.appendChild(text);
            }
        } else {
            handleAlert(data.message);
        }
    }).catch(error => {
        console.error(error);
    });
}

function handleAlert(message) {
    if (!alertShown) {
        alertShown = true;
        alert(message);
        window.location.href = 'mlogin.html';
    }
}
