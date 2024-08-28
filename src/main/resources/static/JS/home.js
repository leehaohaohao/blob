var GData = null;
var announcements = null; // 将announcements变量提升到全局作用域
let baseURL = 'http://localhost:9090/blob/';
let noteSelect = baseURL + 'note/select';
let userInfo = baseURL + 'user/info';
let updateTag = baseURL + 'user/updateTag';
let myCoverPost = baseURL + 'forum/user/unapproval/post';
let likeCollectCoverPost = baseURL + 'forum/my/like/collect/post';
let authorization = localStorage.getItem('authorization');
window.addEventListener('load', function (){
    var noteData = new FormData();
    noteData.append("noteStatus", 0);
    // 获取公告
    fetch(noteSelect, {
        method: 'post',
        body: noteData,
        headers: {
            'Authorization': authorization
        }
    }).then(response => response.json())
        .then(data => {
            if (data.success) {
                // 获取公告数据
                announcements = data.data;

                // 遍历公告数据
                announcements.forEach((announcement, index) => {
                    // 创建一个小方块
                    var block = document.createElement('div');
                    block.className = 'announcementBlock';
                    block.textContent = index + 1; // 小方块显示公告的序号

                    // 设置小方块的点击事件
                    block.onclick = function () {
                        // 显示对应的公告内容
                        document.getElementById('announcementContent').innerHTML = announcement.noteContent;

                        // 移除所有小方块的选中状态
                        var blocks = document.getElementsByClassName('announcementBlock');
                        for (var i = 0; i < blocks.length; i++) {
                            blocks[i].classList.remove('selected');
                        }

                        // 给被点击的小方块添加选中状态
                        block.classList.add('selected');
                    };

                    // 将小方块添加到公告框的右侧
                    document.getElementById('announcementBlocks').appendChild(block);
                });

                // 默认显示第一个公告的内容
                if (announcements.length > 0) {
                    document.getElementById('announcementContent').innerHTML = announcements[0].noteContent;
                    // 默认选中第一个小方块
                    document.getElementsByClassName('announcementBlock')[0].classList.add('selected');
                }
            } else {
                alert(data.message);
            }
        }).catch(error => {
        console.error(error);
    });
    //获取用户信息
    fetch(userInfo, {
        method: 'get',
        headers: {
            'Authorization': authorization
        }
    }).then(response => response.json())
        .then(data => {
            if (data.success) {
                document.getElementsByClassName('avatar')[0].src = data.data.photo;
                document.getElementsByClassName('name')[0].textContent = data.data.name;
                document.getElementsByClassName('uid')[0].textContent = data.data.userId;
                // 更新基本信息
                document.getElementById('postCount').textContent = data.data.post;
                document.getElementById('followerCount').textContent = data.data.followers;
                document.getElementById('followingCount').textContent = data.data.concern;
                if (data.data.selfTag != null) {
                    var tags = data.data.selfTag.split('|').slice(0, 5);  // 从数据中获取标签，并限制最多 5 个
                    var tagsDiv = document.getElementById('tags');
                    tags.forEach((tag, index) => {  // 添加 index 参数
                        var a = document.createElement('a');
                        a.href = 'multiPersonSquare.html?tag=' + encodeURIComponent(tag);  // 替换为你的页面 URL，并将标签添加到查询参数
                        a.textContent = tag;
                        a.style.top = (index * 30) + 'px';  // 每个标签的 top 值根据其索引计算
                        tagsDiv.appendChild(a);
                    });
                }
                GData = data.data;
                sessionStorage.setItem('user', JSON.stringify(GData));
            } else {
                alert(data.message);
                showAnnouncement(0);
            }
        }).catch(error => {
        console.error('Error:', error);
    });
    document.getElementById('publish').addEventListener('click', function () {
        window.location.href = 'post.html';
    });
    var myData = new FormData();
    myData.append("pageNum", 1);
    myData.append("pageSize", 4);
    myData.append("sort", 2);
    fetch(myCoverPost, {
        method: 'post',
        body: myData,
        headers: {
            'Authorization': authorization
        }
    }).then(response => response.json())
        .then(data => {
            if (data.success) {
                var list = data.data;
                var coverList = document.getElementById('MyCoverList');
                coverList.innerHTML = "";
                for (var i = 0; i < list.length; i++) {
                    var cover = document.createElement('div');
                    cover.className = 'cover';

                    var link = document.createElement('a');
                    link.href = 'postDetail.html?postId=' + list[i].postId;

                    var img = document.createElement('img');
                    img.src = list[i].cover;
                    img.alt = 'Cover Image ' + (i + 1);

                    var title = document.createElement('div');
                    title.className = 'title';
                    title.textContent = list[i].title;

                    link.appendChild(img);
                    link.appendChild(title);
                    cover.appendChild(link);
                    coverList.appendChild(cover);
                }
            }
        }).catch(error => {
        console.error(error);
    });
    var likeData = new FormData();
    likeData.append("pageNum", 1);
    likeData.append("pageSize", 4);
    likeData.append("status", 0);
    fetch(likeCollectCoverPost, {
        method: 'post',
        body: likeData,
        headers: {
            'Authorization': authorization
        }
    }).then(response => response.json())
        .then(data => {
            if (data.success) {
                var list = data.data;
                var coverList = document.getElementById('LikeCoverList');
                coverList.innerHTML = "";
                for (var i = 0; i < list.length; i++) {
                    var cover = document.createElement('div');
                    cover.className = 'cover';

                    var link = document.createElement('a');
                    link.href = 'postDetail.html?postId=' + list[i].postId;

                    var img = document.createElement('img');
                    img.src = list[i].cover;
                    img.alt = 'Cover Image ' + (i + 1);

                    var title = document.createElement('div');
                    title.className = 'title';
                    title.textContent = list[i].title;

                    link.appendChild(img);
                    link.appendChild(title);
                    cover.appendChild(link);
                    coverList.appendChild(cover);
                }
            }
        }).catch(error => {
        console.error(error);
    });
    var collectData = new FormData();
    collectData.append("pageNum", 1);
    collectData.append("pageSize", 4);
    collectData.append("status", 1);
    fetch(likeCollectCoverPost, {
        method: 'post',
        body: collectData,
        headers: {
            'Authorization': authorization
        }
    }).then(response => response.json())
        .then(data => {
            if (data.success) {
                var list = data.data;
                var coverList = document.getElementById('CollectCoverList');
                coverList.innerHTML = "";
                for (var i = 0; i < list.length; i++) {
                    var cover = document.createElement('div');
                    cover.className = 'cover';

                    var link = document.createElement('a');
                    link.href = 'postDetail.html?postId=' + list[i].postId;

                    var img = document.createElement('img');
                    img.src = list[i].cover;
                    img.alt = 'Cover Image ' + (i + 1);

                    var title = document.createElement('div');
                    title.className = 'title';
                    title.textContent = list[i].title;

                    link.appendChild(img);
                    link.appendChild(title);
                    cover.appendChild(link);
                    coverList.appendChild(cover);
                }
            }
        }).catch(error => {
        console.error(error);
    });
});
document.getElementById('addTag').addEventListener('click', function (event) {
    // 显示弹出窗口
    document.getElementById('popup').style.display = 'flex';
    var tags = null;
    console.log(GData);
    if (GData != null) {
        // 获取现有的标签
        tags = GData.selfTag ? GData.selfTag.split('|').slice(0, 5) : [];
    }
    // 显示现有的标签
    var tagInputs = document.getElementById('tagInputs');
    tagInputs.innerHTML = ''; // 清空现有的输入框
    console.log(tags);
    if (tags != null) {
        tags.forEach(tag => {
            var input = document.createElement('input');
            input.value = tag;
            input.placeholder = "请输入标签"; // 添加提示
            tagInputs.appendChild(input);
        });
    }
    // 阻止事件冒泡，防止弹出窗口被立即关闭
    event.stopPropagation();
});

document.getElementById('addTagInput').addEventListener('click', function () {
    // 获取现有的输入框数量
    var numInputs = document.getElementById('tagInputs').children.length;

    // 如果输入框数量小于 5，添加一个新的输入框
    if (numInputs < 5) {
        var input = document.createElement('input');
        input.placeholder = "请输入标签"; // 添加提示
        document.getElementById('tagInputs').appendChild(input);
    }
});

document.getElementById('saveTags').addEventListener('click', function () {
    // 获取输入的标签
    var tags = Array.from(document.getElementById('tagInputs').children).map(input => input.value).join('|');
    var formData = new FormData();
    formData.append('selfTag', tags);
    // 发送请求到后端
    fetch(updateTag, {
        method: 'post',
        body: formData,
        headers: {
            'Authorization': authorization
        }
    }).then(response => response.json())
        .then(data => {
            if (data.success) {
                var tagsDiv = document.getElementById('tags');
                tagsDiv.innerHTML = '';
                // 更新前端的标签展示
                var tags = data.data.selfTag.split('|').slice(0, 5);  // 从数据中获取标签，并限制最多 5 个
                tags.forEach((tag, index) => {  // 添加 index 参数
                    var a = document.createElement('a');
                    a.href = '/path/to/your/page?tag=' + encodeURIComponent(tag);  // 替换为你的页面 URL，并将标签添加到查询参数
                    a.textContent = tag;
                    a.style.top = (index * 30) + 'px';  // 每个标签的 top 值根据其索引计算
                    tagsDiv.appendChild(a);
                });
                GData = data.data;
                sessionStorage.setItem('user', JSON.stringify(GData));
            } else {
                alert(data.message);
            }
        }).catch(error => {
        console.error('Error:', error);
    });

    // 隐藏弹出窗口
    document.getElementById('popup').style.display = 'none';
});

// 点击弹出窗口以外的区域，关闭弹出窗口
window.addEventListener('click', function (event) {
    var popup = document.getElementById('popup');
    if (event.target !== popup && !popup.contains(event.target)) {
        popup.style.display = 'none';
    }
});

// 显示指定的公告
function showAnnouncement(index) {
    document.getElementById('announcementContent').innerHTML = announcements[index].content;
}

// 点击 × 号或者公告框以外的区域关闭公告
document.getElementById('closeModal').onclick = function () {
    document.getElementById('modal').style.display = 'none';
}
window.onclick = function (event) {
    if (event.target == document.getElementById('modal')) {
        document.getElementById('modal').style.display = 'none';
    }
}
// 假设你的公告元素有一个ID为'announcementLink'的属性
var announcementLink = document.getElementById('announcementLink');

// 为公告链接添加点击事件监听器
announcementLink.addEventListener('click', function (event) {
    // 阻止链接的默认行为
    event.preventDefault();
    // 显示公告
    document.getElementById('modal').style.display = 'block';
});
