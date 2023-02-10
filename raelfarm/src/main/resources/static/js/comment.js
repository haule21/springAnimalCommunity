/**
 * 
 */ 
var header = $("meta[name='_csrf_header']").attr('content');
var token = $("meta[name='_csrf']").attr('content');
 
function writeComment(iboardnum){
	var content = $('#comment_write_input').val();
	
	if(content.trim() == "" || content == undefined){
		alert("문자를 입력해주세요.");
		return false;
	}
	
	
	$.ajax({
		type : 'POST',
		data : {
			"iboardnum" : iboardnum,
			"content" : content
		},
		url : "/comment/write",
		contentType :   "application/x-www-form-urlencoded",
		beforeSend: function(xhr){
        	xhr.setRequestHeader(header, token);
    	},
		success : function(data){
			window.location.reload();
			console.log(data);
		},
		error : function(request,status,error){
			console.log(request,status,error);
		}
	});
}