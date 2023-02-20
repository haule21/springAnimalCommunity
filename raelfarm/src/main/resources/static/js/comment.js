/**
 * 
 */ 
var header = $("meta[name='_csrf_header']").attr('content');
var token = $("meta[name='_csrf']").attr('content');


function writeComment(iboardnum, endpage){
	var content = $('#comment_write_input').val();
	console.log(endpage);
	
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
			$(".comment_div").load("/comment/view?iboardnum="+iboardnum+"&"+"page="+endpage);
			findPaginationData(Number(endpage));
			$("#cn"+data.commentno).focus();
			$('#comment_write_input').val('');
			console.log(data);
		},
		error : function(request,status,error){
			console.log(request,status,error);
		}
	});
	
	
}

function writeRecomment(iboardnum, parentcommentno, button, page){
	
	var content = $(button).parent().children('textarea.recomment_write_input').val();
	
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
			
			$(".comment_div").load("/comment/view?iboardnum="+iboardnum+"&"+"page="+page);
			findPaginationData(Number(page));
			$("#cn"+data.commentno).focus();
			console.log(data);
			
		}
		
	});
	
}

function viewWriteRecomment(button, lvl, iboardnum, parentcommentno, page){
	var level = parseInt(lvl) + 1;
	
	
	
	$(button).parent().parent().after('<li class="lv'+level+' writeRecomment_li" id="writeRecomment_li">'
	+ '<div class="recomment_write_div_background">'
	+ '<textarea class="recomment_write_input" id="recomment_write_input" placeholder="답글을 남겨주세요"></textarea>'
	+ '<button type="button" class="comment_submit_button "'
								+'onclick="javascript:writeRecomment(\''+iboardnum+'\', '+parentcommentno+', this, '+page+')"></button>'
	+ '</div>'
	+ '</li>');
	$(button).removeAttr("onclick");
	$(button).attr("onclick", "javascript:hideWriteRecomment(this)");
}
function hideWriteRecomment(button){
	console.log($(button).parent().parent().next());
	$(button).parent().parent().next().remove();
	$(button).removeAttr("onclick");
	$(button).attr("onclick", "javascript:viewWriteRecomment(this, this.getAttribute('level'))");
}
