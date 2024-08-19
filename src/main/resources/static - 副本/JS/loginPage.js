let baseURL = 'http://localhost:9090/blob/';
let login_ = baseURL+'login';
let register_ = baseURL+'register';
let emailCode = baseURL+'email/code'
window.onload = function() {
    document.getElementById('loginForm').style.opacity = "1";
}
function switchForm() {
    var loginForm = document.getElementById('loginForm');
    var registerForm = document.getElementById('registerForm');
    if (loginForm.style.opacity === '1') {
        loginForm.style.opacity = '0';
        setTimeout(function() {
            loginForm.style.display = 'none';
            registerForm.style.display = 'block';
            setTimeout(function() {
                registerForm.style.opacity = '1';
            }, 50);
        }, 500);
    } else {
        registerForm.style.opacity = '0';
        setTimeout(function() {
            registerForm.style.display = 'none';
            loginForm.style.display = 'block';
            setTimeout(function() {
                loginForm.style.opacity = '1';
            }, 50);
        }, 500);
    }
}
function login() {
    var email = document.querySelector('#loginForm input[name="email"]').value;
    var password = document.querySelector('#loginForm input[name="psw"]').value;

    var params = new URLSearchParams();
    params.append('email', email);
    params.append('password', password);

    fetch(login_, {
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
                window.location.href='home.html';
            }else{
                alert(data.message);
            }
            // 处理服务器返回的数据
        }).catch(error => {
        console.error('Error:', error);
    });
}
function register() {
    var email = document.querySelector('#registerForm input[name="email"]').value;
    var password = document.querySelector('#registerForm input[name="psw"]').value;
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
function sentCode()
{
    
    var email = document.querySelector('#registerForm input[name="email"]').value;
    if (email==''){
        alert('请输入邮箱！');
        return
    }else{
        var params = new URLSearchParams();
        params.append('email', email);
        fetch(emailCode, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: params,
            credentials:'include'
        }).then(response => response.json())
            .then(res => {
                if(res.success){
                    alert(res.data);
                }else{
                    alert(res.message);
                }
                // 处理服务器返回的数据
            }).catch(error => {
            console.error('Error:', error);
        });
    }
}

document.querySelector('#loginForm .btn').addEventListener('click', login);
document.querySelector('#registerForm .btn').addEventListener('click', register);




