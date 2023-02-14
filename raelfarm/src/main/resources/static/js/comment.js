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
			$(".comment_div").load("/comment/view?iboardnum="+iboardnum);
			$("#cn"+data.commentno).focus();
			console.log(data);
		},
		error : function(request,status,error){
			console.log(request,status,error);
		}
	});
}

function writeRecomment(iboardnum, parentcommentno, button){
	
	var content = $(button).parent().children('input.recomment_write_input').val();
	
	$.ajax({
		type : 'POST',
		data : {
			"iboardnum" : iboardnum,
			"parentcommentno" : parentcommentno,
			"content" : content
		},
		url : "/comment/recomment/write",
		contentType : "application/x-www-form-urlencoded",
		beforeSend: function(xhr){
        	xhr.setRequestHeader(header, token);
    	},
		success : function(data) { 
			
			$(".comment_div").load("/comment/view?iboardnum="+iboardnum);
			$("#cn"+data.commentno).focus();
			console.log(data);
			
		}
		
	});
	
}

function viewWriteRecomment(button){
	$(button).parent().parent('li.comment').children('div.reply_div').css('display','block');
	$(button).removeAttr("onclick");
	$(button).attr("onclick", "javascript:hideWriteRecomment(this)");
}
function hideWriteRecomment(button){
	$(button).parent().parent('li.comment').children('div.reply_div').css('display','none');
	$(button).removeAttr("onclick");
	$(button).attr("onclick", "javascript:viewWriteRecomment(this)");
}
