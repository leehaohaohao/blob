const baseURL = 'http://localhost:9090/blob/';
const userInfo = `${baseURL}user/info`;
const apiStatistics = `${baseURL}back/api`; // 假设这是获取API统计数据的接口
let alertShown = false;
let get = 'get';
let post = 'post';
const authorization = localStorage.getItem('authorization');

window.addEventListener('load', () => {
    showUserInfo();
    showApiStatistics();
});

function showUserInfo() {
    request(userInfo, post, null)
        .then(data => {
            if (data.success) {
                document.getElementById('username').innerText = data.data.name;
            } else {
                handleAlert(data.message);
            }
        });
}

function showApiStatistics() {
    request(apiStatistics, post, null)
        .then(data => {
            if (data.success) {
                populateList(data.data);
            } else {
                handleAlert(data.message);
            }
        });
}

function request(url, method, data) {
    return fetch(url, {
        method: method,
        headers: {
            'Authorization': authorization
        },
        body: data ? data : null
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(response.statusText);
            }
            return response.json();
        })
        .catch(error => {
            console.error('There has been a problem with your fetch operation:', error);
        });
}

function handleAlert(message) {
    if (!alertShown) {
        alertShown = true;
        alert(message);
        if (message === "您还未登陆！" || message === "你没有权限！" || message === "登陆已过期！") {
            window.location.href = 'mlogin.html';
        }
    }
}

function populateList(data) {
    const list = document.getElementById('controllerList');
    list.innerHTML = ''; // 清空列表内容

    Object.keys(data).forEach(controller => {
        const listItem = document.createElement('div');
        listItem.className = 'list-item';
        listItem.innerText = controller;
        listItem.addEventListener('click', () => {
            // 移除之前的高亮
            const activeItem = document.querySelector('.list-item.active');
            if (activeItem) {
                activeItem.classList.remove('active');
            }
            // 添加高亮
            listItem.classList.add('active');
            renderChart(data[controller], controller);
        });
        list.appendChild(listItem);
    });

    // 默认展示第一个图表
    if (list.firstChild) {
        list.firstChild.click();
    }
}


function renderChart(controllerData, controller) {
    const chartContainer = document.getElementById('chartContainer');
    if (!chartContainer) {
        // 创建一个新的图表容器
        const newContainer = document.createElement('div');
        newContainer.id = 'chartContainer';
        newContainer.className = 'chart-container';
        document.getElementById('content').appendChild(newContainer);
    } else {
        // 清空现有的图表容器内容
        chartContainer.innerHTML = '';
    }

    // 获取或创建图表容器
    const container = document.getElementById('chartContainer');

    // 创建一个标题
    const title = document.createElement('h2');
    title.innerText = controller;
    container.appendChild(title);

    // 创建一个canvas元素
    const canvas = document.createElement('canvas');
    container.appendChild(canvas);

    // 准备图表数据
    const labels = controllerData.map(item => item.name);
    const counts = controllerData.map(item => item.count);
    const maxTimes = controllerData.map(item => item.maxTime);
    const avgTimes = controllerData.map(item => item.averageTime);
    const minTimes = controllerData.map(item => item.minTime);

    // 创建图表
    new Chart(canvas, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [
                {
                    label: '调用次数',
                    data: counts,
                    backgroundColor: 'rgba(75, 192, 192, 0.2)',
                    borderColor: 'rgba(75, 192, 192, 1)',
                    borderWidth: 1,
                    yAxisID: 'y1'
                },
                {
                    label: '最大响应时间',
                    data: maxTimes,
                    backgroundColor: 'rgba(255, 99, 132, 0.2)',
                    borderColor: 'rgba(255, 99, 132, 1)',
                    borderWidth: 1,
                    yAxisID: 'y2'
                },
                {
                    label: '平均响应时间',
                    data: avgTimes,
                    backgroundColor: 'rgba(54, 162, 235, 0.2)',
                    borderColor: 'rgba(54, 162, 235, 1)',
                    borderWidth: 1,
                    yAxisID: 'y2'
                },
                {
                    label: '最小响应时间',
                    data: minTimes,
                    backgroundColor: 'rgba(255, 206, 86, 0.2)',
                    borderColor: 'rgba(255, 206, 86, 1)',
                    borderWidth: 1,
                    yAxisID: 'y2'
                }
            ]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            scales: {
                y1: {
                    type: 'linear',
                    position: 'left',
                    beginAtZero: true,
                    title: {
                        display: true,
                        text: '调用次数'
                    }
                },
                y2: {
                    type: 'linear',
                    position: 'right',
                    beginAtZero: true,
                    title: {
                        display: true,
                        text: '响应时间 (ms)'
                    }
                }
            },
            layout: {
                padding: {
                    bottom: 20
                }
            }
        }
    });
}



