// comfirm이 true 이면 th:data-uri="@{|/question/delete/${question.id}|} 해당 URL이 호출
let delete_elements = document.querySelector("#delete-btn");

Array.from(delete_elements).forEach(function(ele) {
	ele.addEventListener('click', function(){
		if(confirm("해당 게시글을 삭제하시겠습니까?")) {
			location.href = this.dataset.uri;
		};
	});
});