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
			console.log(data);
		},
		error : function(request,status,error){
			console.log(request,status,error);
		}
	});
}

function replybutton(iboardnum, commentno, button){

	$(button).parent().children('div.recommentList_block').load("/recomments?"+"iboardnum="+iboardnum+"&commentno="+commentno);
}
function writeRecomment(iboardnum, commentno, content, button){
	
	$.ajax({
		type : 'POST',
		data : {
			"iboardnum" : iboardnum,
			"commentno" : commentno,
			"content" : content
		},
		url : "/comment/recomment/write",
		contentType : "application/x-www-form-urlencoded",
		beforeSend: function(xhr){
        	xhr.setRequestHeader(header, token);
    	},
		success : function(data) { 
			
			$(button).parent().children('div.recommentList_block').load("/recomments?iboardnum="+iboardnum+"&commentno="+commentno);
			$(button).parent().children('div.recommentList_block').focus();
			console.log(data);
			
		}
		
	});
	
}

function viewWriteRecomment(button){
	$(button).parent().children('span.reply').css('display','block');
	$(button).removeAttr("onclick");
	$(button).attr("onclick", "javascript:hideWriteRecomment(this)");
}
function hideWriteRecomment(button){
	$(button).parent().children('span.reply').css('display','none');
	$(button).removeAttr("onclick");
	$(button).attr("onclick", "javascript:viewWriteRecomment(this)");
}

function viewWriteRecommentReply(button, writer){
	var temp = $(button).parent('li.comment_seq').children('span.reply'); 
	temp.css('display','block');
	temp.text(temp.val() + writer);
	viewWriteRecomment(button);
}

function hideRecomments(button){
	$(button).parent('div.recomment_write_div').css('display','none');
}

function viewRecomments(button){
	$(button).parent('li.comment_seq').children('div.recomment_write_div').css('display','block');
}