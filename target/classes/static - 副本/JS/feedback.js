let baseURL = 'http://localhost:9090/blob/';
let errPublish = baseURL+'error/publish';
let getType = baseURL+'error/getType';
document.getElementById('feedback-form').addEventListener('submit', function(event) {
    event.preventDefault();

    var feedbackType = document.getElementById('feedback-type').value;
    var feedback = document.getElementById('feedback').value;
    var image = document.getElementById('img').files[0];
    var formData = new FormData();
    formData.append('type',feedbackType);
    formData.append('content',feedback);
    formData.append('file',image);
    fetch(errPublish,{
        method:'post',
        body: formData,
        credentials:'include'
    }).then(response=>response.json())
    .then(data=>{
        if(data.success){
            alert(data.data);
            window.location.href='home.html';
        }else{
            alert(data.message);
        }
    }).catch(error=>{
        console.error(error);
    })
});

document.getElementById('img').addEventListener('change', function(event) {
    var file = event.target.files[0];
    var url = URL.createObjectURL(file);
    document.getElementById('image-preview').src = url;
});

// 在这里，你可以添加代码来向后端发送请求，获取反馈类型
var feedbackTypes = localStorage.getItem('feedbackTypes');
if (feedbackTypes) {
    feedbackTypes = JSON.parse(feedbackTypes);
    populateFeedbackTypes(feedbackTypes);
} else {
    fetch(getType)
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                // 将反馈类型存储到localStorage
                localStorage.setItem('feedbackTypes', JSON.stringify(data.data));
                populateFeedbackTypes(data.data);
            } else {
                console.error('获取反馈类型失败: ' + data.message);
            }
        })
        .catch(error => {
            console.error('网络错误: ', error);
        });
}

function populateFeedbackTypes(feedbackTypes) {
    var feedbackTypeSelect = document.getElementById('feedback-type');
    feedbackTypes.forEach(function(item) {
        var option = document.createElement('option');
        option.value = item.id;
        option.text = item.type;
        feedbackTypeSelect.add(option);
    });
}
