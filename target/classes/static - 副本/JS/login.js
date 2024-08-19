let baseURL = 'http://localhost:9090/blob/';
let login_ = baseURL+'login';
let register_ = baseURL+'register';
let emailCode = baseURL+'email/code';
var countdown = 60;
var timer = null;
window.onload = function() {
    var loginForm = document.getElementById('loginForm');
    var registerForm = document.getElementById('registerForm');

    // 默认显示登录表单，隐藏注册表单
    loginForm.style.opacity = '1';
    loginForm.style.display = 'block';
    registerForm.style.opacity = '0';
    registerForm.style.display = 'none';
}

function switchForm(event) {
    // 阻止按钮的默认提交行为
    event.preventDefault();

    var loginForm = document.getElementById('loginForm');
    var registerForm = document.getElementById('registerForm');

    if (getComputedStyle(loginForm).opacity == '1') {
        loginForm.style.opacity = '0';
        setTimeout(function(){
            loginForm.style.display = 'none';
            registerForm.style.display = 'block';
            setTimeout(function(){ registerForm.style.opacity = '1'; }, 50);
        }, 500);
    } else {
        registerForm.style.opacity = '0';
        setTimeout(function(){
            registerForm.style.display = 'none';
            loginForm.style.display = 'block';
            setTimeout(function(){ loginForm.style.opacity = '1'; }, 50);
        }, 500);
    }
}

function login(event){
    event.preventDefault();
    var email = document.querySelector('#loginForm input[name="email"]').value;
    var password = document.querySelector('#loginForm input[name="password"]').value;
    var params = new URLSearchParams();
    params.append('email', email);
    params.append('password', password);
    fetch(login_, {
        method: 'POST',
        body: params
    }).then(response => response.json())
        .then(data => {
            if(data.success){
                alert(data.data);
                window.location.href='home.html';
            }else{
                alert(data.message);
            }
            // 处理服务器返回的数据
        }).catch(error => {
        console.error('Error:', error);
    });
}
function register(event) {
    event.preventDefault();
    var email = document.querySelector('#registerForm input[name="email"]').value;
    var password = document.querySelector('#registerForm input[name="password"]').value;
    var code = document.querySelector('#registerForm input[name="code"]').value;

    var params = new URLSearchParams();
    params.append('email', email);
    params.append('password', password);
    params.append('code',code)

    fetch(register_, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: params,
        credentials:'include'
    }).then(response => response.json())
        .then(data => {
            if(data.success){
                alert(data.data);
                window.location.href='loginPage.html';
            }else{
                alert(data.message);
            }
        }).catch(error => {
        console.error('Error:', error);
    });
}
function sentCode(event)
{
    event.preventDefault();
    var email = document.querySelector('#registerForm input[name="email"]').value;
    if (email==''){
        alert('请输入邮箱！');
        return
    }else{
        var params = new URLSearchParams();
        params.append('email', email);
        fetch(emailCode, {
            method: 'POST',
            body: params,
            credentials:'include'
        }).then(response => response.json())
            .then(res => {
                if(res.success){
                    // 禁用按钮并开始倒计时
                    var btn = document.querySelector('#registerForm #submit');
                    btn.disabled = true;
                    timer = setInterval(function() {
                        countdown--;
                        btn.innerText = countdown + '秒后重新获取';
                        if (countdown <= 0) {
                            clearInterval(timer);
                            btn.innerText = '发送验证码';
                            btn.disabled = false;
                            countdown = 60;
                        }
                    }, 1000);
                }
            }).catch(error => {
            console.error('Error:', error);
        });
    }
}