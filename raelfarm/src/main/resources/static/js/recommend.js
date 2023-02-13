/**
 * 
 */
 
var header = $("meta[name='_csrf_header']").attr('content');
var token = $("meta[name='_csrf']").attr('content');
 
function recommend_board(iboardnum, recommend){
	console.log(iboardnum, recommend);
	console.log(typeof(iboardnum), recommend);
	
    $.ajax({
        type : 'POST',
        data : {
			"iboardnum" : iboardnum,
			"recommend" : recommend
		},
        url : "/board/recommend",
        contentType :   "application/x-www-form-urlencoded",
        beforeSend: function(xhr){
        	xhr.setRequestHeader(header, token);
    	},
        success : function(data) {
			$('.recommend_block').load("/board/view/recommend?iboardnum="+iboardnum);
			console.log(data);
		},
		error : function(request,status,error){
			alert("이미 추천을 진행 하였습니다.");
			console.log(request,status,error);
		},
	});
}

function recommend_comment(iboardnum, commentno, seq, recommend, button, count){
	var ireplynum = iboardnum + ":" + commentno + ":" + seq
	var data = {
		"ireplynum" : ireplynum,
		"recommend" : recommend
	}
	console.log(ireplynum, data);
	 
    $.ajax({
        type : 'POST',
        data : data,
        url : "/comment/recommend",
		contentType : "application/x-www-form-urlencoded",
        beforeSend: function(xhr){
        	xhr.setRequestHeader(header, token);
    	},
        success : function(data) {
			if(data.result == "0"){
				$(button).parent().children('span.comment_recomment_count').text(parseInt(count) + 1);	
			}
			else{
				alert("이미 추천한 댓글 입니다.");
			}
			 
		},
		error : function(request,status,error){
			console.log(request,status,error);
		},
	});
}