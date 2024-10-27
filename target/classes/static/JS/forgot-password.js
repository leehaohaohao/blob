let baseURL = 'http://localhost:9090/blob/';
let emailCode = baseURL + 'email/code';
let resetPasswordURL = baseURL + 'forget';
var countdown = 60;
var timer = null;

function sendCode(event) {
    event.preventDefault();
    var email = document.querySelector('#forgotPasswordForm input[name="email"]').value;
    if (email == '') {
        alert('请输入邮箱！');
        return;
    } else {
        var params = new URLSearchParams();
        params.append('email', email);
        fetch(emailCode, {
            method: 'POST',
            body: params
        }).then(response => response.json())
            .then(res => {
                if (res.success) {
                    // 禁用按钮并开始倒计时
                    var btn = document.querySelector('#forgotPasswordForm #submit');
                    btn.disabled = true;
                    timer = setInterval(function () {
                        countdown--;
                        btn.innerText = countdown + '秒后重新获取';
                        if (countdown <= 0) {
                            clearInterval(timer);
                            btn.innerText = '发送验证码';
                            btn.disabled = false;
                            countdown = 60;
                        }
                    }, 1000);
                } else {
                    alert(res.message);
                }
            }).catch(error => {
            console.error('Error:', error);
        });
    }
}

function resetPassword(event) {
    event.preventDefault();
    var email = document.querySelector('#forgotPasswordForm input[name="email"]').value;
    var code = document.querySelector('#forgotPasswordForm input[name="code"]').value;
    var newPassword = document.querySelector('#forgotPasswordForm input[name="newPassword"]').value;
    var confirmPassword = document.querySelector('#forgotPasswordForm input[name="confirmPassword"]').value;

    if (newPassword !== confirmPassword) {
        alert('两次输入的密码不一致！');
        return;
    }
    var formData = new FormData();
    formData.append('email', email);
    formData.append('code', code);
    formData.append('password',newPassword);
    fetch(resetPasswordURL, {
        method: 'POST',
        body: formData
    }).then(response => response.json())
        .then(data => {
            if (data.success) {
                alert(data.data);
                window.location.href = 'login.html';
            } else {
                alert(data.message);
            }
        }).catch(error => {
        console.error('Error:', error);
    });
}
