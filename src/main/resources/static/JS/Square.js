let baseURL = 'http://localhost:9090/blob/';
let gettag = baseURL + 'forum/get/tag';
let getPost = baseURL + 'forum/tag/post';
let userInfo = baseURL + 'user/info';
let socialC = baseURL + 'social/concern';
let isLoading = false;
let PageSize = 16;
let PageNum = 1;
let currentTag = "random_post";
let authorization = localStorage.getItem('authorization');
let lastIndex = 0;
window.addEventListener('load', async function () {
    var userOwn = await getUser();
    console.log(userOwn);
    document.getElementsByClassName('name')[0].innerHTML = userOwn.name;
    document.getElementsByClassName('uid')[0].innerHTML = userOwn.userId;
    await showTagItem();
    // 获取URL参数
    var urlParams = new URLSearchParams(window.location.search);
    var tag = urlParams.get('tag');
    if (tag === '') {
        tag = 'random_post';
    }
    // 如果存在tag参数，将对应的元素设置为active
    if (tag) {
        currentTag = tag;
        var activeTag = document.getElementById(tag);
        if (activeTag == null) {
            document.getElementById('category').innerHTML += "<a class='cate cate_active'>" + tag + "</a>";
        } else {
            activeTag.classList.add('cate_active');
        }
        // console.log(tag)
        var allPost = await getAllPost(tag);
        if (allPost.list.length == 0) {
            showEmptyInfo();
        } else {
            showAllPost(allPost);
        }
    }
});
window.addEventListener('scroll', async function () {
    const scrollPosition = window.innerHeight + window.scrollY;
    const threshold = document.body.offsetHeight * 0.8; // 距离底部20%的位置

    if (scrollPosition >= threshold && !isLoading) {
        isLoading = true; // 设置标志为true，表示正在加载
        PageNum++;
        let newPosts = await getAllPost(currentTag); // 假设 currentTag 是当前标签
        if (newPosts.list.length > 0) {
            showAllPost(newPosts);
        } else {
            PageNum--;
        }
        isLoading = false; // 加载完成后重置标志
    }
});


async function getAllTag() {
    try {
        let response = await fetch(gettag, {
            method: "get",
            headers: {
                'Authorization': authorization
            }
        });
        let res = await response.json();
        if (res.success) {
            return res.data;
        } else {
            alert(res.message);
            console.log(res.message);
        }
    } catch (err) {
        console.error(err);
    }
}

async function showTagItem() {
    var pst_List = await getAllTag();
    // console.log(pst_List);
    let forid = document.getElementById('category');
    let context = "<a class= 'cate' id='random_post' href='multiPersonSquare.html?tag=random_post'>推荐</a>";
    pst_List.forEach(item => {
        context += "<a class= 'cate' id=" + item.tag + " href='multiPersonSquare.html?tag=" + item.tag + "'>" + item.tag + '</a>'
    })
    forid.innerHTML = context;
}

async function getAllPost(tag) {
    try {
        var formData = new FormData();
        formData.append('pageNum', PageNum);
        formData.append('pageSize', PageSize);
        formData.append('tagFuzzy', tag);
        let response = await fetch(getPost, {
            method: "post",
            body: formData,
            headers: {
                'Authorization': authorization
            }
        });
        let res = await response.json();
        if (res.success) {
            return res.data;
        } else {
            alert(res.message);
            console.log(res.message);
        }
    } catch (err) {
        console.error(err);
    }
}

export async function getUser() {
    return fetch(userInfo, {
        method: 'get',
        headers: {
            'Authorization': authorization
        }
    }).then(response => response.json())
        .then(res => {
            if (!res.success) {
                return {
                    "userId": "加载失败",
                    "name": "加载失败",
                }
            }
            // console.log(res.data)
            return res.data
        }).catch(err => {
            console.error(err);
            return {
                "userId": "加载失败",
                "name": "加载失败",
            }
        })
}

window.linkToOne = function (element, id) {
    console.log('click');
    element.className = ('loading followlink');
    element.innerHTML = "<span>.</span><span>.</span><span>.</span>";
    var formData = new FormData();
    formData.append('otherId', id);
    try {
        fetch(socialC, {
            method: 'post',
            body: formData,
            headers: {
                'Authorization': authorization
            }
        }).then(response => response.json())
            .then(res => {
                if (res.success) {
                    element.onclick = null;
                    element.className = "link useLinks";
                    element.innerHTML = "你的关注";
                } else {
                    element.className = 'link followlink';
                    element.innerHTML = "+";
                    alert('网络错误')
                }
            })
            .then(res => {
                getAllPost();
            }).catch(err => {
            console.error(err);
        })
    } catch (err) {
        alert(err);
    }
}

function showAllPost(allPost) {
    console.log(allPost);
    let posts = allPost.list;
    posts.forEach((item, index) => {
        var i = (lastIndex + index) % 4 + 1;
        var target = document.getElementById('imgBox' + i);
        let cont = `
            <information-box>
                <a href='postDetail.html?postId=${item.postId}'>
                    <img src='${item.cover}'>
                    <div class='titleBelowImg'>${item.title}</div>
                </a>
                <tag>
                    ${item.tag.split("|").map(tagItem => `<a href="multiPersonSquare.html?tag=${tagItem}">${tagItem}</a>`).join("")}
                </tag>
                <div class='userBelowImg'>
                    <a class='titleBelow_a' href='center.html?userId=${item.otherInfoDto.userInfoDto.userId}'>
                        <img class='iconOfUse' src='${item.otherInfoDto.userInfoDto.photo}'>
                        <div style='text-indent:0.55em;'>${item.otherInfoDto.userInfoDto.name}</div>
                    </a>
                    ${Status(item.otherInfoDto)}
                </div>
            </information-box>
        `;
        target.innerHTML += cont;
    });
    lastIndex = (lastIndex + posts.length) % 4;
}

function Status(stmt) {
    switch (stmt.status) {
        case(0):
            return "<div class='link followlink' onclick='linkToOne(this,\"" + stmt.userInfoDto.userId + "\")'>+</div>"
                ;
        case(1):
            return "<div class='link useLinks'>你的关注</div>";
        case(2):
            return "<div class='link followlink'>+</div>";
        case(3):
            return "<div class='link useLinks'>互关好友</div>";
        case(4):
            return "<div class='link linksMe'>我</div>";
    }
}

function showEmptyInfo() {
    var emptyTarget = document.getElementById("imageList");
    var cont = "<div style='display:flex;align-items:center;'>" +
        "<div><img style='width:10em' src='http://121.40.154.188:8080/image/emptyPage.png'></div>" +
        "<div><div id='empty_txt'>空&nbsp;空&nbsp;如&nbsp;也</div>" +
        "<div style='font-size:20px;color:#dfdfdf'>这里什么都没有……</div></div>" +
        "</div>"
    console.log(cont)
    emptyTarget.innerHTML = cont;
}