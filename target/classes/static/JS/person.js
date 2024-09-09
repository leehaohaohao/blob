let baseURL = 'http://localhost:9090/blob/';
let personInfo = baseURL + 'back/person';
let userInfo = baseURL + 'user/info';
let updateInfo = baseURL + 'back/update/person'; // 假设这是更新用户信息的URL
let pageNum = 1;
let pageSize = 8;
let status = 0; // 默认状态为0
let alertShown = false; // 全局变量，跟踪是否已经显示过提示框
let authorization = localStorage.getItem('authorization');
window.addEventListener('load', function (){
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
    getPerson(pageNum, pageSize, status);
});

function getPerson(pageNum, pageSize, status) {
    var formData = new FormData();
    formData.append("pageNum", pageNum);
    formData.append("pageSize", pageSize);
    formData.append("status", status);
    fetch(personInfo, {
        method: 'post',
        body: formData,
        headers: {
            'Authorization': authorization
        }
    }).then(res => res.json())
    .then(data => {
        if (data.success) {
            var tbody = document.querySelector("#content table tbody");
            tbody.innerHTML = ""; // 清空表格内容
            data.data.forEach((person, index) => {
                var row = document.createElement("tr");
                row.innerHTML = `
                    <td>${(pageNum - 1) * pageSize + index + 1}</td>
                    <td>${person.name}</td>
                    <td>${person.email}</td>
                    <td>${person.telephone}</td>
                    <td>${person.gender === 0 ? '男' : '女'}</td>
                    <td>${person.lastLoginTime === undefined ? '暂未登陆' : person.lastLoginTime}</td>
                    <td>${person.status === 0 ? '正常' : '异常'}</td>
                    <td><button class="button" data-person='${JSON.stringify(person)}' onclick="openModal(this)">编辑</button> <button class="button" data-person='${JSON.stringify(person)}' onclick="changeStatus(this)">更改状态</button></td>
                `;
                tbody.appendChild(row);
            });

            // 检查数据数量，禁用或启用分页按钮
            document.getElementById('prevPage').disabled = pageNum === 1;
            document.getElementById('nextPage').disabled = data.data.length < pageSize;
        } else {
            handleAlert(data.message);
        }
    }).catch(error => {
        console.error(error);
    });
}

function prevPage() {
    if (pageNum > 1) {
        pageNum--;
        getPerson(pageNum, pageSize, status);
    }
}

function nextPage() {
    pageNum++;
    getPerson(pageNum, pageSize, status);
}

function goToFirstPage() {
    pageNum = 1;
    getPerson(pageNum, pageSize, status);
}

function toggleStatus() {
    var toggleSwitch = document.querySelector('.toggle-switch');
    var statusLabel = document.getElementById('statusLabel');
    if (toggleSwitch.classList.contains('active')) {
        toggleSwitch.classList.remove('active');
        statusLabel.innerText = '正常用户';
        status = 0;
    } else {
        toggleSwitch.classList.add('active');
        statusLabel.innerText = '异常用户';
        status = 1;
    }
    // 重置页码并重新获取数据
    pageNum = 1;
    getPerson(pageNum, pageSize, status);
}

// 获取模态窗口
var modal = document.getElementById("myModal");

// 获取 <span> 元素，设置关闭模态窗口的按钮
var span = document.getElementsByClassName("close")[0];

// 当用户点击按钮，打开模态窗口
function openModal(button) {
    var user = JSON.parse(button.getAttribute('data-person'));
    modal.style.display = "block";
    document.getElementById('name').value = user.name;
    document.getElementById('telephone').value = user.telephone;
    document.getElementById('new-profile-pic').src = user.photo;  // 更改新的图片元素的src，而不是原有的头像图片
    document.getElementById('userId').value = user.userId; // 设置用户ID
    document.getElementById(user.gender === 0 ? 'male' : 'female').checked = true;
}

function changeStatus(button) {
    var user = JSON.parse(button.getAttribute('data-person'));
    var newStatus = status === 0 ? 1 : 0; // 根据当前状态设置新的状态
    var confirmation = confirm(`确定更改此人状态为${newStatus === 0 ? '正常' : '异常'}吗？`);
    if (confirmation) {
        var formData = new FormData();
        formData.append('userId', user.userId);
        formData.append('status', newStatus);
        fetch(updateInfo, {
            method: 'POST',
            body: formData,
            credentials: 'include',
            headers: {
                'Authorization': authorization
            }
        }).then(response => response.json())
            .then(data => {
                if (data.success) {
                    getPerson(pageNum, pageSize, status); // 更新表格数据
                } else {
                    handleAlert(data.message);
                }
            }).catch(error => {
                console.error(error);
            });
    }
}

// 当用户点击 <span> (x), 关闭模态窗口
span.onclick = function () {
    modal.style.display = "none";
}

// 在用户点击任何地方以外的地方关闭模态窗口
window.onclick = function (event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}

// 处理表单提交
document.getElementById('editForm').addEventListener('submit', function (event) {
    event.preventDefault();

    // 这里添加发送 POST 请求的代码
    var formData = new FormData(event.target);
    fetch(updateInfo, {
        method: 'POST',
        body: formData,
        credentials: 'include',
        headers: {
            'Authorization': authorization
        }
    }).then(response => response.json())
        .then(data => {
            if (data.success) {
                modal.style.display = "none";
                getPerson(pageNum, pageSize, status); // 更新表格数据
            } else {
                handleAlert(data.message);
            }
        }).catch(error => {
            console.error(error);
        })
});

// 当用户选择新的图片时，立即在页面上显示新的图片
document.getElementById('img').addEventListener('change', function (event) {
    var reader = new FileReader();
    reader.onload = function (e) {
        document.getElementById('new-profile-pic').src = e.target.result;
    };
    reader.readAsDataURL(event.target.files[0]);
});

function handleAlert(message) {
    if (!alertShown) {
        alertShown = true;
        alert(message);
        window.location.href = 'mlogin.html';
    }
}
