/**
 * 
 */
 
 
// 제목 정규식
let titlerep = /[<>\/\\!@#$%^&*|{}]+/;

function check_board_before_submit(){
	
	var [categorynum, categorynum_number] = [$('#summernote_categorynum').val().trim() == "" ? true : false, $('#summernote_categorynum').val()];
	var [title, title_content] = [$('#summernote_title').val().trim() == "" ? true : false, $('#summernote_title').val()]; 
	var content = $('#summernote_content').val().trim() == "" ? true : false;
	
	if(categorynum || title || content){
		if(categorynum) alert("카테고리를 설정해주세요!"); else if(title) alert("제목을 입력해주세요."); else alert("내용을 입력해주세요."); 
		return false;
	}
	if(titlerep.test(title_content)){
		alert("제목에 사용할 수 없는 문자가 포함 되어 있습니다.");
		return false;
	}
	if(Math.floor(categorynum_number/100) == 4){
		var images = $("input[name=uploaded_images]");
		if(images.length > 0){
			
		}
		else{
			alert("400번 카테고리는 이미지 입력이 필수입니다.");
			return false;
		}
	}
	
	deleteSummernoteImageFile_Submit();
	$("#summernote_form").submit();
}

function check_board_before_modify_submit(){
	deleteSummernoteImageFile_Submit();
	$("#summernote_form").submit();
}
