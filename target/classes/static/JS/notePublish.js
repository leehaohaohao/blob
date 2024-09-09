let baseURL = 'http://localhost:9090/blob/';
let userInfo = baseURL + 'user/info';
let postAnnouncementURL = baseURL + 'note/publish';
let alertShown = false; // 全局变量，跟踪是否已经显示过提示框
let authorization = localStorage.getItem('authorization');
window.addEventListener('load', function () {
    const E = window.wangEditor;
    const editor = new E('#editor');
    editor.create();
    fetch(userInfo, {
        method: 'get',
        headers: {
            'Authorization': authorization
        }
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
    // 默认选择系统公告
    document.querySelector('.status-option[data-status="1"]').classList.add('active');
    document.getElementById('status').value = '1';

    document.querySelectorAll('.status-option').forEach(option => {
        option.addEventListener('click', function () {
            document.querySelectorAll('.status-option').forEach(opt => opt.classList.remove('active'));
            this.classList.add('active');
            document.getElementById('status').value = this.getAttribute('data-status');
        });
    });

    document.getElementById('announcementForm').addEventListener('submit', function (event) {
        event.preventDefault();
        // 确保富文本编辑器的内容被传递到textarea中
        document.getElementById('content').value = editor.txt.html();
        let formData = new FormData();
        formData.append('status', document.getElementById('status').value);
        formData.append('content', document.getElementById('content').value);

        fetch(postAnnouncementURL, {
            method: 'POST',
            body: formData,
            credentials: 'include'
        }).then(response => response.json())
            .then(data => {
                if (data.success) {
                    alert('公告发布成功');
                    event.target.reset(); // 清空表单
                    editor.txt.clear(); // 清空富文本编辑器
                    document.querySelectorAll('.status-option').forEach(opt => opt.classList.remove('active'));
                    // 重新默认选择系统公告
                    document.querySelector('.status-option[data-status="1"]').classList.add('active');
                    document.getElementById('status').value = '1';
                } else {
                    handleAlert(data.message);
                }
            }).catch(error => {
            console.error('网络错误: ', error);
        });
    });
});

function handleAlert(message) {
    if (!alertShown) {
        alertShown = true;
        alert(message);
        if(message==="您还未登陆！" || message==="你没有权限！"){
            window.location.href = 'mlogin.html';
        }
    }
}
