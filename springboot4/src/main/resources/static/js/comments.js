/**
 * /post/details.html 파일에 포함.
 * 댓글 보기/등록/수정/삭제 기능(Ajax 요청/응답 처리).
 */
document.addEventListener('DOMContentLoaded', () => {
    // 현재 댓글 페이지 번호 -> 댓글 [더보기] 버튼에서 이용하기 위해서
    let currentPageNo = 0;
    
    // div#collapseComments HTML 요소를 bootstrap Collapse 객체로 생성
    const bsCollapse = new bootstrap.Collapse('div#collapseComments', {toggle: false});
    
    // button#btnToggle 버튼을 찾고, 클릭 이벤트 이벤트 리스너를 설정
    const btnToggle = document.querySelector('button#btnToggle');
    btnToggle.addEventListener('click', () => {
        bsCollapse.toggle(); // Collapse 객체를 보기/숨기기 토글.
        
        const dataToggle = btnToggle.getAttribute('data-toggle');
        if (dataToggle === 'collapse') {
            btnToggle.innerHTML = '댓글 숨기기';
            btnToggle.setAttribute('data-toggle', 'unfold');
            
            // 첫번째 페이지(p=0) 댓글 목록 가져오기
            getAllComments(0);
        } else {
            btnToggle.innerHTML = '댓글 보기';
            btnToggle.setAttribute('data-toggle', 'collapse');
        }
    });
    
    // 댓글 [더보기] 버튼에 클릭 이벤트 리스너를 설정
    const btnMore = document.querySelector('button#btnMore');
    btnMore.addEventListener('click', () => getAllComments(currentPageNo + 1));
    
    // 댓글 [등록] 버튼에 클릭 이벤트 리스너를 설정
    const btnRegisterComment = document.querySelector('button#btnRegisterComment');
    btnRegisterComment.addEventListener('click', registerComment);
    
    
    /* ---------- 함수 선언 ---------- */
    // 파라미터 pageNo에 전달된 페이지의 댓글 목록 가져오기.
    async function getAllComments(pageNo = 0) {
        // 댓글들이 달려 있는 포스트 아이디:
        const postId = document.querySelector('input#id').value;
        
        // Ajax 요청을 보낼 url:
        const uri = `/api/comment/all/${postId}?p=${pageNo}`;
        
        // Ajax 요청을 보내고 응답/에러 처리.
        /*
        axios
        .get(uri) // 비동기 함수 호출
        .then((response) => { 성공 콜백 구현부 }) // 성공 콜백 등록
        .catch((error) => { 실패 콜백 구현부 }); // 실패 콜백 등록
        */
        
        try {
            // 비동기 함수 호출
            /*
            const response = await axios.get(uri);
            const data = response.data;
            */
            const { data } = await axios.get(uri);
            
            // 성공 콜백에서 할 일들을 작성.
            console.log(data);
            currentPageNo = data.page.number; // 현재 댓글 목록의 페이지 번호를 저장.
            makeCommentElements(data); // 댓글 목록을 html 요소로 작성해서 보여줌.
        } catch (error) {
            // 실패 콜백에서 할 일들을 작성.
            console.log(error);
        }
        
    }
    
    // 댓글 목록을 html 요소로 만들어서 div에 추가.
    /*
    function makeCommentElements(data) {
        const content = data.content;
        const page = data.page;
    }
    */
    function makeCommentElements({ content, page }) {
        // 댓글 목록을 추가할 div 요소
        const divComments = document.querySelector('div#divComments');
        
        // div에 삽입할 HTML 문자열(댓글 목록)
        let htmlStr = '';
        for (const comment of content) {
            htmlStr += `
            <div class="mt-2 card card-body">
                <div class="mt-2" style="font-size: 0.5em">
                    <span class="fw-bold">${comment.writer}<span>
                    <span class="fw-light text-secondary">${comment.modifiedTime}</span>
                </div>
                <div class="mt-2">
                    <div class="mt-2">
                        <textarea class="commentText form-control" 
                            data-id="${comment.id}">${comment.text}</textarea>
                    </div>
                    <div class="mt-2">
                        <button class="btnDelete btn btn-sm btn-outline-danger"
                            data-id="${comment.id}">삭제</button>
                        <button class="btnUpdate btn btn-sm btn-outline-primary"
                            data-id="${comment.id}">수정</button>
                    </div>
                </div>
            </div>
            `;
        }
        
        if (currentPageNo === 0) {
            // 댓글 목록 첫번째 페이지이면, 기존 내용을 다 지우고 새로 작성.
            divComments.innerHTML = htmlStr;
        } else {
            // 댓글 목록의 첫번째 페이지가 아니면, 기존 내용 밑에 댓글 목록을 추가.
            divComments.innerHTML += htmlStr;
        }
        
        // 댓글 [삭제], [수정] 버튼을 찾고, 클릭 이벤트 리스너를 설정.
        const btnDeletes = document.querySelectorAll('button.btnDelete');
        /*
        for (const btn of btnDeletes) {
            btn.addEventListener('click', deleteComment);
        }
        */
        btnDeletes.forEach((btn) => btn.addEventListener('click', deleteComment));
        
        const btnUpdates = document.querySelectorAll('button.btnUpdate');
        btnUpdates.forEach((btn) => btn.addEventListener('click', updateComment));
    }
    
    // 댓글 등록 함수
    async function registerComment() {
        // 댓글이 등록될 포스트 아이디
        const postId = document.querySelector('input#id').value;
        
        // 댓글 내용
        const text = document.querySelector('textarea#commentText').value;
        
        // 댓글 작성자
        const writer = document.querySelector('input#commentWriter').value;
        
        if (text.trim() === '') {
            alert('댓글 내용은 반드시 입력해야 합니다.');
            return;
        }
        
        // Ajax 요청에서 Request Body에 포함시켜서 전송할 데이터
        const reqBody = { postId, text, writer };
        
        // Ajax 요청을 보내고, 응답/에러 처리
        try {
            const { data } = await axios.post('/api/comment', reqBody);
            console.log(data);
            
            // 댓글 입력 textarea의 내용을 지움.
            document.querySelector('textarea#commentText').value = '';
            
            // 댓글 목록을 다시 그림.
            getAllComments(0);
            
        } catch (error) {
            console.log(error);
        }
        
    }
    
    // 댓글 삭제 요청 처리 함수
    async function deleteComment(event) {
//        console.log(event.target);
        const check = confirm('정말 삭제할까요?');
        if (!check) {
            return;
        }
        
        const id = event.target.getAttribute('data-id');
        const uri = `/api/comment/${id}`;
        try {
            const response = await axios.delete(uri);
            console.log(`deleted comment id = ${response.data}`);
            alert('댓글이 삭제됐습니다.');
            getAllComments(0);
        } catch (error) {
            console.log(error);
        }
        
    }
    
    // 댓글 수정 요청 처리 함수
    async function updateComment(event) {
//        console.log(event.target);
        const id = event.target.getAttribute('data-id'); // 댓글 아이디
        const uri = `/api/comment/${id}`; // Ajax 요청 주소
        
        const textarea = document.querySelector(`textarea.commentText[data-id="${id}"]`);
//        console.log(textarea);
        const text = textarea.value; // 댓글 내용
        if (text.trim() === '') {
            alert('댓글 내용은 반드시 입력해야 합니다.');
            return;
        }
        
        const check = confirm('변경된 내용을 저장할까요?');
        if (!check) {
            return;
        }
        
        try {
            const response = await axios.put(uri, { id, text });
            console.log(`updated comment id = ${response.data}`);
            alert('댓글 업데이트 성공');
            getAllComments(0);
        } catch (error) {
            console.log(error);
        }
        
    }
    
});