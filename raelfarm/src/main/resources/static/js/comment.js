/**
 * 
 */ 
var header = $("meta[name='_csrf_header']").attr('content');
var token = $("meta[name='_csrf']").attr('content');

/*<![CDATA[*/
	let viewreply = '<div class="recomment_write_div">'
					+ '<ul th:each="recomment : $recommentList">'
	            	+ '<li class="recomment">'
	            			+ '<span class="recomment_writer">[[${recomment.writer}]] </span>'
	            			+ '<span class="recomment_recommend"> '
	            			+ '<button type="button" '
	            			+ 'th:iboardnum="${recomment.iboardnum}"'
	            			+ 'th:commentno="${recomment.commentno}"'
	            			+ 'th:onclick="javascript:recommend_comment(this.getAttribute(\'iboardnum\'), this.getAttribute(\'commentno\'), this.getAttribute(\'seq\'))">추천</button>&nbsp; [[${recomment.recommendcount}]] </span>'
	            			+ '<span class="recomment_norecommend"> <button>비추천</button>&nbsp; [[${recomment.norecommendcount}]] </span>'
	            			+ '<th:block th:if="${recomment.registereddate == recomment.modifieddate}">'
	            				+ '<span class="recomment_date"> [[${recomment.registereddate}]] </span>'
	            			+ '</th:block>'
	            			+ '<th:block th:unless="${recomment.registereddate == recomment.modifieddate}">'
	            				+ '<span class="recomment_date"> (수정됨)[[${recomment.modifieddate}]] </span>'
	            			+ '</th:block>'
	            			+ '<div class="recomment_content_div">'
	            				+ '<p class="recomment_content">[[${recomment.content}]]</p>'
	            			+ '</div>'
	            		+ '</li>'
	            		+ '<button ' 
            				+ 'type="button" '
            				+ 'th:recommentwriter="${recomment.writer}" ' 
            				+ 'th:onclick="javascript:viewWriteRecommentReply(this, this.getAttribute(\'recommentwriter\'))">답글</button>'	
	            	+ '</ul>'
	            	+ '<button type="button" th:onclick="javascript:hideRecomments(this)">답글 숨기기</button>'
	            + '</div>';
/*]]>*/


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

function replybutton(iboardnum, commentno, button){
	if(iboardnum == null){
		return false;
	}
	$.ajax({
		type : 'POST',
		data : {
			"iboardnum" : iboardnum,
			"commentno" : commentno 
		},
		url : "/comment/viewRecomments",
		contentType : "application/x-www-form-urlencoded",
		beforeSend: function(xhr){
        	xhr.setRequestHeader(header, token);
    	},
		success : function(data) { 
			console.log(data);
			$(button).after(viewreply);
			$(button).removeAttr("onclick");
			$(button).attr("onclick", "javascript:hideRecomments_(this)");
		}
		
	});
}
function writeRecomment(iboardnum, commentno, content){
	
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
			console.log(success);
			
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
	$(button).parent('li.comment_seq').children('span.reply').css('display','block');
	$(button).parent('li.comment_seq').children('span.reply').text = writer;
}

function hideRecomments(button){
	$(button).parent('div.recomment_write_div').css('display','none');
}

function hideRecomments_(button){
	$(button).parent('li.comment_seq').children('div.recomment_write_div').css('display','none');
	$(button).removeAttr("onclick");
	$(button).attr("onclick", "javascript:viewRecomments(this)");
}

function viewRecomments(button){
	$(button).parent('li.comment_seq').children('div.recomment_write_div').css('display','block');
	$(button).removeAttr("onclick");
	$(button).attr("onclick", "javascript:hideRecomments_(this)");
}