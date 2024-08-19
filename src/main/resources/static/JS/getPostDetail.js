let baseURL = 'http://localhost:9090/blob/';
import { getUser } from './Square.js';
var userOwn = null;
var target = null;
var postID = null;
var targetName = null;
var pageLike = 0;
var pageCollect = 0;
window.onload = async function(){
    userOwn = await getUser();
    console.log(userOwn);
    document.getElementsByClassName('name')[0].innerHTML=userOwn.name;
    document.getElementsByClassName('uid')[0].innerHTML=userOwn.userId;
    var urlParams = new URLSearchParams(window.location.search);
    postID = urlParams.get('postId');
    // console.log(postID)
    document.getElementById('form_').addEventListener('submit', function(event){
        event.preventDefault();
        sentComment();
    });
    let postData = await getPostByID(postID);
}
async function request(url,mtd,FD){
    console.log(baseURL + url)
    const config={
        method:mtd,
        body:FD
    }
    try{
        const response = await fetch(baseURL + url, config);
        
        if (!response.ok) {
          throw new Error('网络错误');
        }
        return response.json()
    }catch(err){
        throw new Error('请求失败: ' + err.message);
    }
}
function renderComments(comments, isParent = true, parentName = '') {
    let result = '';
    for (let i = 0; i < comments.length; i++) {
        let comment = comments[i];
        let className = isParent ? 'remark pare' : 'remark rinnerCmmt';
        let idbox = isParent ? "id='"+comment.commentId+"'" : ''
        let replyString = isParent ? `${comment.userName}` : `${comment.userName}▶${parentName}: `;
        result += `<div class="${className}"${idbox}>
        <div class='rinnerCmmt_Head'>
            <img src=${comment.photo} class="remkPersonPic">
            ${replyString}
        </div>
        <div onclick="changeV('${comment.userName}','${comment.commentId}')">
        <p class='remark_P'>${comment.commentContent}</p>
        <p style='font-size:10px;color:#bdbdbd'>回复</p>
        </div></div>`;
        if (comment.childCommentDto) {
            result += renderComments(comment.childCommentDto, false, comment.userName);
        }
    }
    return result;
}
async function getPostByID(postID){
    console.log(postID)
    try{
        var fd = new FormData();
        fd.append( 'postId', postID );
        // console.log(fd.get('postId'));
        var response = request('forum/id/post','post',fd);
        let doc = document;
        let RD = null;
        response.then(res=>{
            RD=res.data
        }).then(res=>{
            let dateTime = new Date(RD.postTime)
            doc.getElementById('post_time').innerHTML = dateTime.getHours()+":"
            +dateTime.getMinutes()+":"
            +dateTime.getSeconds();
            doc.getElementById('post_date').innerHTML = dateTime.getFullYear()+
            "年"+`${dateTime.getMonth()+1}`+
            "月"+dateTime.getDay()+"日";
    
            doc.getElementById('post_title').innerHTML = "&nbsp;"+RD.title;
    
            doc.getElementById('postOwnerPic').src = RD.photo;
            doc.getElementById('tags').innerHTML = RD.tag.split('|').map(function(item){
                return `<a href='multiPersonSquare.html?tag=${item}'>${item}</a>`
            }).join('');
            doc.getElementById('post_author_box').href = "center.html?userID="+RD.userId
            doc.getElementById('post_author').innerHTML = RD.name;
            doc.getElementById('post_detail').innerHTML = RD.postContent;
        }).then(res=>{
            var REMARK = RD.parentCommentDto;
            document.getElementById('remkCont').innerHTML = renderComments(REMARK)
        }).then(res=>{
            pageLike = RD.postLike;
            pageCollect = RD.collect;
            console.log(pageCollect)
            console.log(RD.isCollect)
            var collect = "<span style='color:#808080' id='collect'>"+pageCollect+'</span>&nbsp;&nbsp;'
            var like = "<span style='color:#808080' id='like'>"+pageLike+'</span>&nbsp;&nbsp;'
            if (RD.isCollect){
                collect += `<span style = 'color:#ff664b' onclick="CollectOrLike(this,'${postID}',1,1)">已收藏</span>`
            }else{
                collect += `<span onclick="CollectOrLike(this,'${postID}',0,1)">收藏</span>`
            }
            if (RD.isLove){
                like += `<span style = 'color:#ff664b' onclick="CollectOrLike(this,'${postID}',1,0)">已点赞</span>`
            }else{
                like += `<span onclick="CollectOrLike(this,'${postID}',0,0)">点赞</span>`
            }
            doc.getElementById('collectThisPost').innerHTML = collect;
            doc.getElementById('likeThisPost').innerHTML = like;
        }).catch(err=>{
            alert(err);
        })
    }catch(err){
        console.error(err);
    }
}
window.CollectOrLike  = function(e,postid,status,type){
    console.log('click');
    let fd =new FormData();
    fd.append('postId',postid);
    fd.append('status',status);
    fd.append('type',type);
    let txt = ''
    let div = '';
    let data = 0;
    if (type == 0){
        txt = '点赞'
        status == 1 ? pageLike -=1 :pageLike +=1
        data = pageLike
        div = document.getElementById('like')
    }else{
        txt = '收藏'
        status == 1 ? pageCollect -=1 :pageCollect +=1
        data = pageCollect
        div = document.getElementById('collect')
    }
    // console.log(data)
    if (status == 1){
        div.innerHTML = data;
        e.innerHTML = txt
        e.onclick = function() { CollectOrLike(this,postid,0,type); }
        e.style.color = '#000'
    }else{
        div.innerHTML = data;
        e.innerHTML = '已'+txt
        e.style.color ="#ff664b";
        e.onclick = function() { CollectOrLike(this,postid,1,type); }
    } 
    var response = request("forum/love/collect","post",fd);
    response.then(res=>{
        if(!res.success){
            if (type == 0){
                status == 1 ? pageLike +=1 :pageLike -=1
                data = pageLike
            }else{
                status == 1 ? pageCollect +=1 :pageCollect -=1
                data = pageCollect
            }
            if (status == 0){
                div.innerHTML = data;
                e.innerHTML = txt
                e.onclick = function() { CollectOrLike(this,postid,0,type); }
                e.style.color = '#000'
            }else{
                div.innerHTML = data;
                e.innerHTML = '已'+txt
                e.style.color ="#ff664b";
                e.onclick = function() { CollectOrLike(this,postid,1,type); }
            } 
        }
    }).catch(err=>{
        console.error(err)
    })
}
window.iptfocus=function (event){
    document.getElementById("form_").style.border = "2px solid #8dabf6"
}
window.losefocus=function (event){
    document.getElementById('input_').placeholder = '评论该帖子'
    document.getElementById("form_").style.border = "2px solid #e4e4e4"
}
function sentComment(){
    let fd = new FormData();
    let iptV = document.getElementById('input_').value
    console.log(iptV)
    fd.append('postId',postID);
    fd.append('parentId',target);
    fd.append('commentContent',iptV)
    let response = request("social/comment",'post',fd);
    response.then(res=>{
        if(res.success){
            createCMMT(target,iptV,res.data.topId,res.data.commentId)
            document.getElementById('input_').value = '';
            document.getElementById("form_").style.border = "2px solid #e4e4e4"
        }
    }).then(res=>{
    target = null;
    targetName = null;

    })
}
function createCMMT(inC,content,intoid,selfid){
    if (inC == null){
        let CMMTCLASS = document.getElementById('remkCont');
        let newComment = document.createElement('div');
        newComment.className='remark pare '
        newComment.id = selfid
        newComment.innerHTML = `
            <div class='rinnerCmmt_Head'>
                <img src=${userOwn.photo} class="remkPersonPic">
                ${userOwn.name}
            </div>
            <div onclick="changeV('${userOwn.name}','${selfid}')">
            <p class='remark_P'>${content}</p>
            <p style='font-size:10px;color:#bdbdbd'>回复</p>
            </div>`;
        CMMTCLASS.insertBefore(newComment, CMMTCLASS.firstChild)
    }else{
        let CMMTCLASS = document.getElementById(intoid);
        let newComment = document.createElement('div');
        newComment.className='remark rinnerCmmt'
        newComment.innerHTML = `<div class='rinnerCmmt_Head'>
            <img src=${userOwn.photo} class="remkPersonPic">
            ${userOwn.name}▶${targetName}
        </div>
        <div onclick="changeV('${userOwn.name}','${selfid}')">
        <p class='remark_P'>${content}</p>
        <p style='font-size:10px;color:#bdbdbd'>回复</p>
        </div>`
        CMMTCLASS.after(newComment)
    }
}
window.changeV = function (value,id){
    target = id
    targetName = value
    document.getElementById('input_').placeholder = '回复：'+value
    document.getElementById("form_").style.border = "2px solid #8dabf6"
}