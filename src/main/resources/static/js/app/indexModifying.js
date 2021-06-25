// 나중에 짬나면 제이쿼리 싹 없애보자
const elements = {
    title:document.getElementById("title"),
    author: document.getElementById("author"),
    content: document.getElementById("content")
}

const fns = {
    save: ()=>{
        const data = {
            title: elements.title.innerText,
            author: elements.author.innerText,
            content: elements.content.innerText,
        }

        fetch('/api/v1/posts', {
            type:'POST',
            dataType:'json',

        })


    },
    update: ()=>{

    },
    delete: ()=>{

    }
}

const init = () => {
    const btnSave = document.getElementById("btn-save");
    const btnUpdate = document.getElementById("btn-update");
    const btnDelete = document.getElementById("btn-delete");

    btnSave.addEventListener('click', fns.save);
    btnUpdate.addEventListener('click', fns.update);
    btnDelete.addEventListener('click', fns.delete);
}

var main = {

    save: function(){
        var data = {
            title: $('#title').val(),
            author: $('#author').val(),
            content: $('#content').val()
        };

        $.ajax({
            type:'POST',
            url:'/api/v1/posts',
            dataType:'json',
            contentType:'application/json; charset=utf-8',
            data:JSON.stringify(data)
        }).done(function(){
            alert('글이 등록되었습니다.');
            window.location.href = '/';
        }).fail(function (err){
            alert(JSON.stringify(err));
        });
    },
    update: function(){
        var data = {
            title: $('#title').val(),
            content: $('#content').val()
        };

        var id = $('#id').val();

        $.ajax({
            type:'PUT',
            url:'/api/v1/posts/' + id,
            dataType:'json',
            contentType:'application/json; charset=utf-8',
            data:JSON.stringify(data)
        }).done(function(){
            alert('글이 수정되었습니다.');
            window.location.href = '/';
        }).fail(function (err){
            alert(JSON.stringify(err));
        });
    },
    delete: function(){
        var id = $('#id').val();

        $.ajax({
            type:'DELETE',
            url:'/api/v1/posts/' + id,
            dataType:'json',
            contentType:'application/json; charset=utf-8',
        }).done(function(){
            alert('글이 삭제되었습니다.');
            window.location.href = '/';
        }).fail(function (err){
            alert(JSON.stringify(err));
        });
    }
}

main.init();