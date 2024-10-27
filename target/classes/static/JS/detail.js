let baseURL = 'http://localhost:9090/blob/';
let userInfo = baseURL + 'user/info';
let approvalDetail = baseURL + 'forum/id/approval/post';
let approval = baseURL+'forum/approval/post';
let alertShown = false; // 全局变量，跟踪是否已经显示过提示框
let authorization = localStorage.getItem('authorization');
window.addEventListener('load', function() {
    fetch(userInfo, {
        method: 'get',
        headers: {
            'Authorization': authorization
        },
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

    // 获取postId并调用approvalDetail接口
    let urlParams = new URLSearchParams(window.location.search);
    let postId = urlParams.get('postId');
    if (postId) {
        fetchApprovalDetail(postId);
    }
});


function handleAlert(message) {
    if (!alertShown) {
        alertShown = true;
        alert(message);
        //window.location.href = 'mlogin.html';
    }
}

function fetchApprovalDetail(postId) {
    let fd = new FormData();
    fd.append('postId', postId);

    fetch(approvalDetail, {
        method: 'post',
        body: fd,
        headers: {
            'Authorization': authorization
        },
    }).then(res => res.json())
    .then(data => {
        if (data.success) {
            renderApprovalDetail(data.data);
        } else {
            handleAlert(data.message);
        }
    }).catch(error => {
        console.error(error);
    });
}

function renderApprovalDetail(data) {
    let doc = document;
    let dateTime = new Date(data.postTime);
    doc.getElementById('post_time').innerHTML = dateTime.getHours() + ":" + dateTime.getMinutes() + ":" + dateTime.getSeconds();
    doc.getElementById('post_date').innerHTML = dateTime.getFullYear() + "年" + `${dateTime.getMonth() + 1}` + "月" + dateTime.getDay() + "日";
    doc.getElementById('post_title').innerHTML = " " + data.title;
    doc.getElementById('postOwnerPic').src = data.photo;
    doc.getElementById('tags').innerHTML = data.tag.split('|').map(function(item) {
        return `<a href='multiPersonSquare.html?tag=${item}'>${item}</a>`;
    }).join('');
    doc.getElementById('post_author_box').href = "center.html?userID=" + data.userId;
    doc.getElementById('post_author').innerHTML = data.name;
    doc.getElementById('post_detail').innerHTML = data.postContent;
    doc.getElementById('coverImage').src = data.cover; 
    doc.getElementById('approveButton').setAttribute('onclick', `approveArticle('${data.postId}', 0)`);
    doc.getElementById('rejectButton').setAttribute('onclick', `approveArticle('${data.postId}', 3)`);
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
        },
    }).then(res=>res.json())
    .then(data=>{
        if(data.success){
            window.location.href="approval.html";
        }else{
            handleAlert(data.message);
        }
    })
}
