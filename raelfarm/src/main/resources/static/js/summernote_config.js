var header = $("meta[name='_csrf_header']").attr('content');
var token = $("meta[name='_csrf']").attr('content');
let titlerep = /[<>\/\\!@#$%^&*|{}]+/;
const DeleteList = { data : new FormData(),
					setData : function(dataname, data){
						this.data.append(dataname, data );
					},
					getData : function(){
						return this.data;
					} };

function summernote_config(){
	$('#summernote').summernote({
		height: 1000,
		minHeight: null,
		maxHeight: null,
		focus: true,                  // set focus to editable area after initializing summernote
		lang: 'ko-KR',
		// disableResizeEditor : true,
		toolbar: [
          ['style', ['style']],
          ['font', ['bold', 'underline', 'clear']],
          ['color', ['color']],
          ['para', ['ul', 'ol', 'paragraph']],
          ['table', ['table']],
          ['insert', ['link', 'picture', 'video']],
          ['view', ['fullscreen', 'help']]
        ],
		callbacks: {	//여기 부분이 이미지를 첨부하는 부분
			onImageUpload : function(files) {
				uploadSummernoteImageFile(files[0], this);
			},
			onPaste: function (e) {
				var clipboardData = e.originalEvent.clipboardData;
				if (clipboardData && clipboardData.items && clipboardData.items.length) {
					var item = clipboardData.items[0];
					if (item.kind === 'file' && item.type.indexOf('image/') !== -1) {
						e.preventDefault();
					}
				}
			},
			onMediaDelete : function(files) {
				deleteSummernoteImageFile(files[0], this);
				
			},
            onChange : function (contents) {
                $('#summernote_content').val(contents);
            }
        }
	});	
}

function uploadSummernoteImageFile(file, editor){
	data = new FormData();
	data.append("file", file);
	
	$.ajax({
		data : data,
		type : "POST",
		url : "/uploadSummernoteImageFile",
		contentType : false,
		processData : false,
		beforeSend: function(xhr){
        	xhr.setRequestHeader(header, token);
    	},
		success : function(data) {
			console.log(data);
        	//항상 업로드된 파일의 url이 있어야 한다.
			$(editor).summernote('insertImage', data.url);
			$("#summernote_form").append("<input name=\"uploaded_images\" type=\"hidden\" value=\""+data.url+"\" readonly/>");
		}
	});
}

function deleteSummernoteImageFile(file, editor){
	
		
	// data = new FormData();
	
		
	var images = $("input[name=uploaded_images]");
	
	if(images.length === 1){
		DeleteList.setData("file", $("input[name=uploaded_images]").val())
		//data.append("file", $("input[name=uploaded_images]").val());
		$("input[name=uploaded_images]").remove();
	}
	else{
		for(let i=0; i < images.length; i++){
			console.log(images[i]);
			// /summernote/1996/05/24/filename
			var imagesdata = images[i].value.split("/");
			imagesdata.forEach(a=>console.log(a));
			
			// https://locahost/summernote/1996/05/24/filename
			var filesrc = file.src.split("/");
			
			console.log(imagesdata, " : ", filesrc);
			
			if(imagesdata[5] == filesrc[7]){
				console.log(images[i].value);
				// data.append("file",images[i].value);
				DeleteList.setData("file",images[i].value)
				images[i].remove();
			} 	
		}
	}
	
}

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
	
	deleteFileAll();
	$("#summernote_form").submit();
}

function check_board_before_modify_submit(){
	var [title, title_content] = [$('#summernote_title').val().trim() == "" ? true : false, $('#summernote_title').val()]; 
	var content = $('#summernote_content').val().trim() == "" ? true : false;
	
	if(title || content){
		if(title) alert("제목을 입력해주세요."); else alert("내용을 입력해주세요."); 
		return false;
	}
	if(titlerep.test(title_content)){
		alert("제목에 사용할 수 없는 문자가 포함 되어 있습니다.");
		return false;
	}
	deleteFileAll();
	$("#summernote_form").submit();
}

function deleteFileAll(){
		$.ajax({
		data : DeleteList.getData(),
		type : "POST",
		url : "/deleteSummernoteImageFile",
		contentType : false,
		processData : false,
		beforeSend: function(xhr){
        	xhr.setRequestHeader(header, token);
    	},
		success : function(data) {
			console.log(data);	
		}
	});
}
