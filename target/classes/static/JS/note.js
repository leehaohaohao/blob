let baseURL = 'http://localhost:9090/blob/';
let announcementInfo = baseURL + 'note/manager/select';
let userInfo = baseURL + 'user/info';
let editNote = baseURL +'note/manager/update';
let pageNum = 1;
let pageSize = 8;
let noteStatus = 0; // 默认状态为0
let noteType = 0; // 默认类别为0
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
    getAnnouncements(pageNum, pageSize, noteStatus, noteType);
}

function getAnnouncements(pageNum, pageSize, noteStatus, noteType) {
    var formData = new FormData();
    formData.append("pageNum", pageNum);
    formData.append("pageSize", pageSize);
    formData.append("noteStatus", noteStatus);
    formData.append("noteType", noteType);
    fetch(announcementInfo, {
        method: 'post',
        body: formData
    }).then(res => res.json())
    .then(data => {
        if (data.success) {
            var tbody = document.querySelector("#content table tbody");
            tbody.innerHTML = ""; // 清空表格内容
            data.data.forEach((announcement, index) => {
                var row = document.createElement("tr");
                row.innerHTML = `
                    <td>${(pageNum - 1) * pageSize + index + 1}</td>
                    <td>${announcement.noteType === 0 ? '系统' : '活动'}</td>
                    <td>${announcement.noteContent}</td>
                    <td>${announcement.name}</td>
                    <td>${announcement.noteDate}</td>
                    <td>${announcement.noteStatus === 0 ? '正常' : '删除'}</td>
                    <td><button class="button" <button class="button" data-announcement='${JSON.stringify(announcement)}' onclick="deleteAnnouncement(this)">${noteStatus===0?'删除':'重新发布'}</button></td>
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
        getAnnouncements(pageNum, pageSize, noteStatus, noteType);
    }
}

function nextPage() {
    pageNum++;
    getAnnouncements(pageNum, pageSize, noteStatus, noteType);
}

function goToFirstPage() {
    pageNum = 1;
    getAnnouncements(pageNum, pageSize, noteStatus, noteType);
}

function handleAlert(message) {
    if (!alertShown) {
        alertShown = true;
        alert(message);
        //window.location.href = 'mlogin.html';
    }
}


function deleteAnnouncement(button) {
    var announcement = JSON.parse(button.getAttribute('data-announcement'));
    var formData  = new FormData();
    formData.append("noteId",announcement.noteId);
    formData.append("noteStatus",announcement.noteStatus===1?0:1);
    fetch(editNote,{
        method:'post',
        body:formData
    }).then(res=>res.json())
    .then(data=>{
        if(data.success){
            getAnnouncements(pageNum, pageSize, noteStatus, noteType);
        }else{
            handleAlert(data.message);
        }
    }).catch(error=>{
        alert(error);
    })
}

document.getElementById('publishBtn').addEventListener('click', function() {
    window.location.href = 'notePublish.html';
});

// 添加状态滑块和类别滑块的逻辑
document.querySelectorAll('.toggle-switch').forEach(toggle => {
    toggle.addEventListener('click', function() {
        this.classList.toggle('active');
        if (this.id === 'statusToggle') {
            noteStatus = this.classList.contains('active') ? 1 : 0;
            document.getElementById('statusLabel').innerText = noteStatus === 0 ? '正常公告' : '删除公告';
        } else if (this.id === 'typeToggle') {
            noteType = this.classList.contains('active') ? 1 : 0;
            document.getElementById('typeLabel').innerText = noteType === 0 ? '系统公告' : '活动公告';
        }
        // 重置页码并重新获取数据
        pageNum = 1;
        getAnnouncements(pageNum, pageSize, noteStatus, noteType);
    });
});
