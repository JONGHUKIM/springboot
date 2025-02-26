/**
 * /post/modify.html 파일에 포함
 */

document.addEventListener('DOMContentLoaded', () => {

    // 삭제, 업데이트 버튼을 찾음
    const updateForm = document.querySelector('#updateForm');
    const id = document.querySelector('#id');
    const title = document.querySelector('#title').value.trim();
    const content = document.querySelector('#content').value.trim();
    // string.trim(): 문자열 시작과 끝에 있는 모든 공백을 제거
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
    
    // 업데이트 버튼에 'click' 이벤트 리스너를 설정
    btnUpdate.addEventListener('click', () => {
         // 제목과 내용이 비어있는지 확인
     if (title.value === '' || content === '' ) {
         alert('제목과 내용은 반드시 입력하세요.');
         return;
     }
     
      const check = confirm('변경된 내용을 저장할까요?');
      if (check) {       
          updateForm.method = 'post';
          updateForm.action = 'update';
          updateForm.submit();
      }
      
     });
    
});