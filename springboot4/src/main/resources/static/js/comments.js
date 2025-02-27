/**
 * /post/details.html 파일에 포함
 * 댓글 보기/등록/수정/삭제 기능(Ajax 요청/응답 처리)
 */
document.addEventListener('DOMContentLoaded', () => {
   // div#collapseComments HTML 요소를 bootstrap Collapse 객체로 생성
   const bsCollapse = new bootstrap.Collapse('div#collapseComments', {toggle: false});
    
   // button#btnToggle 버튼을 찾고, 클릭 이벤트 리스너를 설정
   const btnToggle = document.querySelector('button#btnToggle');
   btnToggle.addEventListener('click', () => {
     bsCollapse.toggle(); // Collapse 객체를 보기/숨기기 토글
     
     const dataToggle = btnToggle.getAttribute('data-toggle');
     if (dataToggle === 'collapse') {
        btnToggle.innerHTML = '댓글 숨기기';
        btnToggle.setAttribute('data-toggle', 'unfold');
     } else {
        btnToggle.innerHTML = '댓글 보기';
        btnToggle.setAttribute('data-toggle', 'collapse');
     }
   });
});