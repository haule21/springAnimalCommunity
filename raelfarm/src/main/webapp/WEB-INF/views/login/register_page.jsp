<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="_csrf" content="${_csrf.token}"/>
	<meta name="_csrf_header" content="${_csrf.headerName}">
	<title>register page</title>
	<script src="/js/jquery-3.6.2.min.js"></script>
	<script src="/js/checkingid.js"></script>

</head>
<body>
	<script>
	$(function() {
		$( "#userid" ).on("blur change keyup paste", function(){
			$(this).val( $(this).val().replace(/[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]| |[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]/gi, '' ));
			if(userid != $('#userid').val){
				idck = 0;
				console.log("user.id : " +user.id + " idck : " + idck);
			}
		});
		console.log("idck : "+idck);
	})
		
	$(function() {
		$( "#recheck-password" ).on("blur input", function(){
			if($(this).val == $( "#keyup" ).val && pwck == 0){
				alert('동일한 패스워드 입니다.');
				pwck = 1;
			}
			else{
				pwck = 0;
			}
			console.log("pwck : "+pwck)
		});	
	})
	
	</script>
	<form id='register_form' onsubmit="return false;">
		<input type="hidden" value="${_csrf.token}" name="${_csrf.parameterName}">
		<ul>
			<li>
				<label for='userid'>* 아이디</label>		
				<input type='text' id='userid' name='userid' autocomplete='username' maxlength='16'> <button type="button" id="idck" onclick="javascript:clickidcheck()">아이디 중복 확인</button>
				
			</li>
			<li>
				<label for='password'>* 패스워드 </label>
				<input type='password' name='password' id='password' maxlength='36' autocomplete="new-password"  >
			</li>
			<li>
				<label for='recheck-password'>* 패스워드 확인</label>
				<input type='password' name='recheck-password' id='recheck-password' maxlength='36' autocomplete="new-password"  >
			</li>
			
			<li>
				<label for='name'>* 이름</label>
				<input type='text' id='name' maxlength='7'>
			</li>
			<li>
				<label for='nickname'>* 닉네임</label>
				<input type='text' id='nickname' maxlength='11'>
			</li>
			<li>
				<label for='email'>* 이메일</label>
				<input type='text' id='email'> 
			</li>
			<li>
				<label for='phonenumber'>* 전화번호</label>
				<input type='number' id='phonenumber'> <button type='button'>전화번호 인증</button> 
			</li>
			<li>
				<label for='address'>주소</label>
				<input type='text' id='address'>
			</li>
		</ul>
	</form>

	
</body>
</html>