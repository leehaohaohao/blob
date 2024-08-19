const E = window.wangEditor
const editor = new E('#editor')
editor.create()
let baseURL = 'http://localhost:9090/blob/';
let userInfo  = baseURL+'user/info';
let post = baseURL+'forum/post';
var coverFile = null;
let authorization = localStorage.getItem('authorization');
// Get user data from sessionStorage
var user = JSON.parse(sessionStorage.getItem('user'));
if (user == null) {
    fetch(userInfo, {
        method: 'get',
        credentials:'include',
        headers: {
            'Authorization': authorization
        }
    }).then(response => response.json())
        .then(data => {
            if (data.success) {
                user = data.data;
                sessionStorage.setItem('user', JSON.stringify(data.data));
            } else {
                alert(data.message);
            }
        }).catch(error => {
            console.error('Error:', error);
        });
}
if (user != null) {
    document.getElementById('avatar').src = user.photo;
    document.getElementById('name').textContent = user.name;
    document.getElementById('uid').textContent += user.userId;
}

document.getElementById('addTag').addEventListener('click', function() {
    var tagInputs = document.getElementById('tag-inputs');
    if (tagInputs.children.length < 5) {
        var input = document.createElement('input');
        input.type = 'text';
        input.placeholder = '添加标签';
        tagInputs.appendChild(input);
    }
});
document.getElementById('submit').addEventListener('click', function (event) {
    event.preventDefault();

    var title = document.getElementById('title').value;
    var content = editor.txt.html();
    var tags = Array.from(document.getElementById('tag-inputs').children).map(input => input.value);
    postArticle(title, content, tags);
});
document.getElementById('cover').addEventListener('change', function (event) {
    coverFile = event.target.files[0];
    var url = URL.createObjectURL(coverFile);
    document.getElementById('cover-preview').src = url;
});

function postArticle(title, content, tags) {
    // 创建一个FormData对象
    var formData = new FormData();
    formData.append('title', title);
    formData.append('post_content', content);
    formData.append('tags', tags.join('|'));
    formData.append('file',coverFile);
    // 发送POST请求
    fetch(post, {
        method: 'POST',
        body: formData,
        credentials:'include',
        headers: {
            'Authorization': authorization
        }
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                alert('文章发布成功');
                window.location.href='post.html';
                console.log('文章发布成功');
            } else {
                alert(data.message);
                // 请求失败，可以在这里处理失败的逻辑
                console.log('文章发布失败: ' + data.message);
            }
        })
        .catch(error => {
            // 网络错误，可以在这里处理网络错误的逻辑
            console.error('网络错误: ', error);
        });
}
