let baseURL = 'http://localhost:9090/blob/';
let articleInfo = baseURL + 'forum/approval/list';
let userInfo = baseURL + 'user/info';
let approval = baseURL+'forum/approval/post';
let pageNum = 1;
let pageSize = 8;
let alertShown = false; // 全局变量，跟踪是否已经显示过提示框
let authorization = localStorage.getItem('authorization');
window.onload = function() {
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
    getArticles(pageNum, pageSize);
}

function getArticles(pageNum, pageSize) {
    var formData = new FormData();
    formData.append("pageNum", pageNum);
    formData.append("pageSize", pageSize);
    fetch(articleInfo, {
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
            data.data.forEach((article, index) => {
                var row = document.createElement("tr");
                row.innerHTML = `
                    <td>${(pageNum - 1) * pageSize + index + 1}</td>
                    <td>${article.otherInfoDto.userInfoDto.name}</td>
                    <td><a href="detail.html?postId=${article.postId}">${article.title}</a></td>
                    <td>${article.tag.includes('|') ? article.tag.split('|').join(', ') : article.tag}</td>
                    <td>${formatDate(article.postTime)}</td>
                    <td><button class="button" onclick="approveArticle('${article.postId}',0)">批准</button> <button class="button" onclick="approveArticle('${article.postId}',3)">拒绝</button></td>
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
        getArticles(pageNum, pageSize);
    }
}

function nextPage() {
    pageNum++;
    getArticles(pageNum, pageSize);
}

function goToFirstPage() {
    pageNum = 1;
    getArticles(pageNum, pageSize);
}

function handleAlert(message) {
    if (!alertShown) {
        alertShown = true;
        alert(message);
        //window.location.href = 'mlogin.html';
    }
}

function approveArticle(postId,status) {
    var formData = new FormData();
    formData.append("postId",postId);
    formData.append("postStatus",status);
    // 批准文章逻辑
    fetch(approval,{
        method:'post',
        body:formData,
        headers: {
            'Authorization': authorization
        }
    }).then(res=>res.json())
    .then(data=>{
        if(data.success){
            getArticles(pageNum, pageSize);
        }else{
            handleAlert(data.message);
        }
    })
}


function formatDate(dateString) {
    const date = new Date(dateString);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    const seconds = String(date.getSeconds()).padStart(2, '0');
    return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
}
