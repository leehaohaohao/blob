let baseURL = 'http://localhost:9090/blob/';
let login_ = baseURL+'manager/login';

var countdown = 60;
var timer = null;
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
                window.location.href='manager.html';
            }else{
                alert(data.message);
            }
            // 处理服务器返回的数据
        }).catch(error => {
        console.error('Error:', error);
    });
}
