let baseURL = 'http://localhost:9090/blob/';
import { getUser } from './Square.js';
var userOwn = null;
var target = null;
var postID = null;
var iptV = null;
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
        let replyString = isParent ? `${comment.userName}` : `${comment.userName}▶${parentName}: `;
        result += `<div class="${className}">
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
            doc.getElementById('post_author_box').href = "individualSpace.html?userID="+RD.userId
            doc.getElementById('post_author').innerHTML = RD.name;
            doc.getElementById('post_detail').innerHTML = RD.postContent;
        }).then(res=>{
            var REMARK = RD.parentCommentDto;
            document.getElementById('remkCont').innerHTML = renderComments(REMARK)
        }).catch(err=>{
            alert(err);
        })
    }catch(err){
        console.error(err);
    }
}
window.iptfocus=function (event){
    document.getElementById("form_").style.border = "2px solid #8dabf6"
}
window.losefocus=function (event){
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
            
            document.getElementById('input_').value = '';
            document.getElementById("form_").style.border = "2px solid #e4e4e4"
        }
    })
}
window.changeV = function (value,id){
    target = id
    document.getElementById('input_').placeholder = '回复：'+value
    document.getElementById("form_").style.border = "2px solid #8dabf6"
}