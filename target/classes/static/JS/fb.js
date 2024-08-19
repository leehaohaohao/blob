let baseURL = 'http://localhost:9090/blob/';
let userInfo = baseURL + 'user/info';
let approvalDetail = baseURL + 'forum/id/approval/post';
let approval = baseURL+'forum/approval/post';
let alertShown = false; // 全局变量，跟踪是否已经显示过提示框
window.onload = function() {
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
}
function handleAlert(message) {
    if (!alertShown) {
        alertShown = true;
        alert(message);
        //window.location.href = 'mlogin.html';
    }
}

