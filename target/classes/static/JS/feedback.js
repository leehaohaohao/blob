let baseURL = 'http://localhost:9090/blob/';
let userInfo = baseURL + 'user/info';
let errPublish = baseURL + 'error/publish';
let getType = baseURL + 'error/getType';
let authorization = localStorage.getItem('authorization');

window.addEventListener('load', function() {
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
            }
        }).catch(error => {
        console.error(error);
    });
});

document.getElementById('feedback-form').addEventListener('submit', function(event) {
    event.preventDefault();

    var feedbackType = document.getElementById('feedback-type').value;
    var feedback = document.getElementById('feedback').value;
    var image = document.getElementById('img').files[0];
    var formData = new FormData();
    formData.append('status', feedbackType);
    formData.append('content', feedback);
    formData.append('file', image);
    fetch(errPublish, {
        method: 'post',
        body: formData,
        headers: {
            'Authorization': authorization
        }
    }).then(response => response.json())
        .then(data => {
            if (data.success) {
                alert(data.data);
                window.location.href = 'home.html';
            } else {
                alert(data.message);
            }
        }).catch(error => {
        console.error(error);
    });
});

document.getElementById('img').addEventListener('change', function(event) {
    var file = event.target.files[0];
    var url = URL.createObjectURL(file);
    document.getElementById('image-preview').src = url;
});

// 获取反馈类型
fetch(getType, {
    headers: {
        'Authorization': authorization
    }
})
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            populateFeedbackTypes(data.data);
        } else {
            console.error('获取反馈类型失败: ' + data.message);
        }
    })
    .catch(error => {
        console.error('网络错误: ', error);
    });

function populateFeedbackTypes(feedbackTypes) {
    var feedbackTypeSelect = document.getElementById('feedback-type');
    feedbackTypes.forEach(function(item) {
        var option = document.createElement('option');
        option.value = item.id;
        option.text = item.type;
        feedbackTypeSelect.add(option);
    });
}
