/**
 * /post/modify.html 파일에 포함
 */

document.addEventListener('DOMContentLoaded', () => {

    // 삭제 버튼을 찾음
    const updateForm = document.querySelector('#updateForm');
    const id = document.querySelector('#id');
    const title = document.querySelector('#title');
    const content = document.querySelector('#content');
    const btnDelete = document.querySelector('#btnDelete');
    const btnUpdate = document.querySelector('#btnUpdate');
    // 삭제 버튼에 'click' 이벤트 리스너를 설정
    // 이벤트 리스너는 서버로 삭제 요청을 보냄
    
    // 삭제 버튼에 'click' 이벤트 리스너를 설정.
    btnDelete.addEventListener('click', () => {
        // 사용자 확인 후 서버로 삭제 요청을 보냄.
        const check = confirm('정말 삭제할까요?');
        if (check) {
            const postId = document.querySelector('input#id').value;
            location.href = `/post/delete?id=${postId}`;
        }
    });
    
});