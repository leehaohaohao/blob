const baseURL = 'http://localhost:9090/blob/';
const numUrl = `${baseURL}back/num`;
const userInfo = `${baseURL}user/info`;
const hotPost = `${baseURL}back/hot/post`;
const hotTag = `${baseURL}back/hot/tag`;
let alertShown = false;
const authorization = localStorage.getItem('authorization');

window.addEventListener('load', () => {
    fetchData(numUrl, updateNumbers);
    fetchData(userInfo, updateUserInfo);
    fetchHotPosts();
    fetchHotTags();
});

async function fetchData(url, callback) {
    try {
        const response = await fetch(url, {
            method: 'get',
            headers: {
                'Authorization': authorization
            }
        });
        const data = await response.json();
        if (data.success) {
            callback(data.data);
        } else {
            handleAlert(data.message);
        }
    } catch (error) {
        console.error(error);
    }
}

async function fetchHotPosts() {
    const formData = new FormData();
    formData.append("pageNum", 1);
    formData.append("pageSize", 6);

    try {
        const response = await fetch(hotPost, {
            method: 'post',
            body: formData,
            headers: {
                'Authorization': authorization
            }
        });
        const data = await response.json();
        if (data.success) {
            data.data.forEach(item => {
                createAndAppendElement("r-container", "div", "post", `postDetail.html?postId=${item.postId}`, item.title);
            });
        } else {
            handleAlert(data.message);
        }
    } catch (error) {
        console.error(error);
    }
}

async function fetchHotTags() {
    const formData = new FormData();
    formData.append("pageNum", 1);
    formData.append("pageSize", 6);

    try {
        const response = await fetch(hotTag, {
            method: 'post',
            body: formData,
            headers: {
                'Authorization': authorization
            }
        });
        const data = await response.json();
        if (data.success) {
            data.data.forEach(item => {
                createAndAppendElement("l-container", "div", "text", `multiPersonSquare.html?tag=${item.tag}`, item.tag);
            });
        } else {
            handleAlert(data.message);
        }
    } catch (error) {
        console.error(error);
    }
}

function updateNumbers(data) {
    const ctx1 = document.getElementById('dataChart1').getContext('2d');
    const ctx2 = document.getElementById('dataChart2').getContext('2d');

    new Chart(ctx1, {
        type: 'bar',
        data: {
            labels: ['用户数', '违规用户数', '群组数', '文章数', '待审核文章数', '话题数'],
            datasets: [{
                label: '数量',
                data: [data.userNum, data.uuserNum, data.groupNum, data.postNum, data.upostNum, data.tagNum],
                backgroundColor: [
                    'rgba(75, 192, 192, 0.2)',
                    'rgba(255, 99, 132, 0.2)',
                    'rgba(255, 206, 86, 0.2)',
                    'rgba(54, 162, 235, 0.2)',
                    'rgba(153, 102, 255, 0.2)',
                    'rgba(255, 159, 64, 0.2)'
                ],
                borderColor: [
                    'rgba(75, 192, 192, 1)',
                    'rgba(255, 99, 132, 1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(54, 162, 235, 1)',
                    'rgba(153, 102, 255, 1)',
                    'rgba(255, 159, 64, 1)'
                ],
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });

    new Chart(ctx2, {
        type: 'doughnut',
        data: {
            labels: ['用户数', '违规用户数', '群组数', '文章数', '待审核文章数', '话题数'],
            datasets: [{
                label: '数量分布',
                data: [data.userNum, data.uuserNum, data.groupNum, data.postNum, data.upostNum, data.tagNum],
                backgroundColor: [
                    'rgba(75, 192, 192, 0.2)',
                    'rgba(255, 99, 132, 0.2)',
                    'rgba(255, 206, 86, 0.2)',
                    'rgba(54, 162, 235, 0.2)',
                    'rgba(153, 102, 255, 0.2)',
                    'rgba(255, 159, 64, 0.2)'
                ],
                borderColor: [
                    'rgba(75, 192, 192, 1)',
                    'rgba(255, 99, 132, 1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(54, 162, 235, 1)',
                    'rgba(153, 102, 255, 1)',
                    'rgba(255, 159, 64, 1)'
                ],
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: {
                    position: 'top',
                },
                tooltip: {
                    callbacks: {
                        label: function(context) {
                            let label = context.label || '';
                            if (label) {
                                label += ': ';
                            }
                            if (context.parsed !== null) {
                                label += context.parsed;
                            }
                            return label;
                        }
                    }
                }
            }
        }
    });
}

function updateUserInfo(data) {
    document.getElementById('username').innerText = data.name;
}

function createAndAppendElement(containerId, elementType, className, href, innerText) {
    const container = document.getElementById(containerId);
    const element = document.createElement(elementType);
    element.className = className;
    const a = document.createElement("a");
    a.href = href;
    a.innerText = innerText;
    element.appendChild(a);
    container.appendChild(element);
}

function handleAlert(message) {
    if (!alertShown) {
        alertShown = true;
        alert(message);
        if (message === "您还未登陆！" || message === "你没有权限！") {
            window.location.href = 'mlogin.html';
        }
    }
}
