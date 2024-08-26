let baseURL = 'http://localhost:9090/blob/';
let feedbackInfo = baseURL + 'error/get';
let userInfo = baseURL + 'user/info';
let updateFeedbackStatus = baseURL + 'error/update'; // 假设这是更新反馈状态的URL
let pageNum = 1;
let pageSize = 4;
let alertShown = false; // 全局变量，跟踪是否已经显示过提示框
let authorization = localStorage.getItem('authorization');

window.addEventListener('load', function() {
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
    getFeedback(pageNum, pageSize);
});


function getFeedback(pageNum, pageSize) {
    var formData = new FormData();
    formData.append("pageNum", pageNum);
    formData.append("pageSize", pageSize);
    fetch(feedbackInfo, {
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
                data.data.forEach((feedback, index) => {
                    var row = document.createElement("tr");
                    row.innerHTML = `
                    <td>${(pageNum - 1) * pageSize + index + 1}</td>
                    <td>${feedback.type}</td>
                    <td>${feedback.content}</td>
                    <td>${feedback.time}</td>
                    <td><img src="${feedback.file}" alt="反馈图片" style="max-width: 100px; max-height: 100px;"></td>
                    <td>${feedback.name}</td>
                    <td><button class="button" data-feedback='${JSON.stringify(feedback)}' onclick="markAsCompleted(this)">完成</button></td>
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
        getFeedback(pageNum, pageSize);
    }
}

function nextPage() {
    pageNum++;
    getFeedback(pageNum, pageSize);
}

function goToFirstPage() {
    pageNum = 1;
    getFeedback(pageNum, pageSize);
}

function markAsCompleted(button) {
    var feedback = JSON.parse(button.getAttribute('data-feedback'));
    var confirmation = confirm(`确定将此反馈标记为完成吗？`);
    if (confirmation) {
        var data = {
            feedbackId: feedback.feedbackId
        };
        fetch(updateFeedbackStatus, {
            method: 'POST',
            body: JSON.stringify(data),
            headers: {
                'Content-Type': 'application/json',
                'Authorization': authorization
            }
        }).then(response => response.json())
            .then(data => {
                if (data.success) {
                    getFeedback(pageNum, pageSize); // 更新表格数据
                } else {
                    handleAlert(data.message);
                }
            }).catch(error => {
            console.error(error);
        });
    }
}


function handleAlert(message) {
    if (!alertShown) {
        alertShown = true;
        alert(message);
        //window.location.href = 'mlogin.html';
    }
}
