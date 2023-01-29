var header = $("meta[name='_csrf_header']").attr('content');
var token = $("meta[name='_csrf']").attr('content');

function summernote_config(){
	$('#summernote').summernote({
		height: 850,                 // set editor height
		minHeight: 850,             // set minimum height of editor
		maxHeight: 850,             // set maximum height of editor
		focus: true,                  // set focus to editable area after initializing summernote
		lang: 'ko-KR',
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
	data = new FormData();
	data.append("file", file.src);
	
	$.ajax({
		data : data,
		type : "POST",
		url : "/deleteSummernoteImageFile",
		contentType : false,
		processData : false,
		beforeSend: function(xhr){
        	xhr.setRequestHeader(header, token);
    	},
		success : function(data) {
			var images = $("input[name=uploaded_images]");
			if(images.length === 1){
				$("input[name=uploaded_images]").remove();
			}
			else{
				for(let i=0; i < images.length; i++){
					if(images[i].value.trim() == data.filePath){
						images[i].remove();
					} 	
				}
			}
		}
	});
}
