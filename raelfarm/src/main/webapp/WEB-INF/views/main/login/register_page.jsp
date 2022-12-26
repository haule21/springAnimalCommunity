<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="_csrf" content="${_csrf.token}"/>
	<meta name="_csrf_header" content="${_csrf.headerName}">
	<title>register page</title>
	<link rel="stylesheet" href="css/register-page.css">
	<script src="/js/jquery-3.6.2.min.js"></script>
	<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<script src="/js/checkingid.js"></script>
	
	<script>
	function execPostCode() {
	    new daum.Postcode({
	        oncomplete: function(data) {
	            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분입니다.

	            // 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
	            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
	            var roadAddr = data.roadAddress; // 도로명 주소 변수
	            var extraRoadAddr = ''; // 참고 항목 변수

	            // 법정동명이 있을 경우 추가한다. (법정리는 제외)
	            // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
	            if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
	                extraRoadAddr += data.bname;
	            }
	            // 건물명이 있고, 공동주택일 경우 추가한다.
	            if(data.buildingName !== '' && data.apartment === 'Y'){
	               extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
	            }
	            // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
	            if(extraRoadAddr !== ''){
	                extraRoadAddr = ' (' + extraRoadAddr + ')';
	            }

	            // 우편번호와 주소 정보를 해당 필드에 넣는다.
	            $("#address").val(data.zonecode);
	            $("#address_detail1").val(roadAddr);

	            var guideTextBox = document.getElementById("guide");
	            // 사용자가 '선택 안함'을 클릭한 경우, 예상 주소라는 표시를 해준다.
	            if(data.autoRoadAddress) {
	                var expRoadAddr = data.autoRoadAddress + extraRoadAddr;
	                guideTextBox.innerHTML = '(예상 도로명 주소 : ' + expRoadAddr + ')';
	                guideTextBox.style.display = 'block';

	            } else if(data.autoJibunAddress) {
	                var expJibunAddr = data.autoJibunAddress;
	                guideTextBox.innerHTML = '(예상 지번 주소 : ' + expJibunAddr + ')';
	                guideTextBox.style.display = 'block';
	            } else {
	                guideTextBox.innerHTML = '';
	                guideTextBox.style.display = 'none';
	            }
	        }
	    }).open({ autoClose: true });
	}

</script>

</head>
<body>
	<script>
	let passwordcheck = { pwdck : (pw) => user.pwCheck(pw)} 
	
	// userid 한글, 초성, 자음, 특수문자, 공백 replace
	$(function() {
		$( "#userid" ).on("blur change keyup paste", function(){
			$(this).val( $(this).val().replace(/[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]| |[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]/gi, '' ));
			if(user.id != $('#userid').val){
				
				idck = 0;
				console.log("user.id : " +user.id + " idck : " + idck);
			}
		});
		console.log("idck : "+idck);
	})
	// phone-number
	$(function() {
		$( "#phonenumber" ).on("blur change keyup paste", function(){
			$(this).val( $(this).val().replace(/[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]|[a-zA-Z]| |[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]/gi, '' ));
		});
	})
	// nickname 
	$(function() {
		$( "#nickname" ).on("blur change keyup paste", function(){
			$(this).val( $(this).val().replace(/| |[\{\}\[\]\/?.,;:|\)*~`!^\-+<>@\#$%&\\\=\(\'\"]/gi, '' ));
			if(user.nickname != $('#nickname').val){
				$("#nickname").css({"border-color" : "#ff0000" });
				$("#nickname-description").text("");
				nick = 0;
			}
		});
		console.log("idck : "+idck);
	})
	// 이름 한글만 가능
	$(function() {
		$( "#name" ).on("blur change keyup paste", function(){
			$(this).val( $(this).val().replace(/| |[\{\}\[\]\/?.,;:|\)*~`!^\-+<>@\#$%&\\\=\(\'\"]/gi, '' ));
			if($( "#name" ).val().length > 0){
				$("#name").css({"border-color" : "#0982f0" });
				$("#name-description").text("");
			}
			else{
				$("#name").css({"border-color" : "#ff0000" });
				$("#name-description").text("");
			}
		});
	})
	// 패스워드 확인 란 동일 패스워드 확인
	$(function() {
		$( "#recheck-password" ).on("keyup", function(){
			if($("#recheck-password").val().length > 0 && $(this).val() == $("#password").val()){
				$( "#recheck-password" ).css({"border-color" : "#0982f0" });
				$("#recheck-description").css({"color" : "#0982f0" });
				$("#recheck-description").text("");
				pwck = 1;
			}
			else{
				$( "#recheck-password" ).css({"border-color" : "#ff0000" });
				$("#recheck-description").css({"color" : "#ff0000" });
				$("#recheck-description").text("패스워드가 다릅니다.");
				pwck = 0;
			}
		});	
	})
	// 패스워드 채크
	$(function() {
		$( "#password" ).on("keyup", function(){ 
			if(user.password != $('#password').val()){
				passwordcheck.pwdck( $(this).val() );
				pwck = 0;	
			}
			
		});	
	})
	
	// 닉네임 특수문자, 초성, 자음 replace
	$(function(){
		$("#nickname").on("keyup", function(){$(this).val( $(this).val().replace(/[ㄱ-ㅎ|ㅏ-ㅣ| |[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]/gi, '' ));});
	})
	
	
	</script>
	<form id='register_form' action="register_page/register" method="POST">
		<input type="hidden" value="${_csrf.token}" name="${_csrf.parameterName}">
		<ul class="register_ul">
			<li>
				<label for='userid'>* 아이디</label>		
				<input type='text' id='userid' name='userid' autocomplete='username' maxlength='16'> <button type="button" id="idck" onclick="javascript:clickidcheck()">아이디 중복 확인</button>
				<span id='userid-description'></span>
			</li>
			<li>
				<label for='password'>* 패스워드 </label>
				<input type='password' name='password' id='password' maxlength='36' autocomplete="new-password"  >
				<span id='password-description'></span>
			</li>
			<li>
				<label for='recheck-password'>* 패스워드 확인</label>
				<input type='password' name='recheck-password' id='recheck-password' maxlength='36' autocomplete="new-password"  >
				<span id='recheck-description'></span>
			</li>
			
			<li>
				<label for='name'>* 이름</label>
				<input type='text' name='name' id='name' maxlength='7'>
				<span id='name-description'></span>
			</li>
			<li>
				<label for='nickname'>* 닉네임</label>
				<input type='text' name='nickname' id='nickname' maxlength='11'> <button type="button" id="nick" onclick="javascript:clicknicknamecheck()">닉네임 중복 확인</button>
				<span id='nickname-description'></span>
			</li>
			<li>
				<label for='email'>* 이메일</label>
				<input type='text' name='email' id='email' style="width:200px"> 
				<span id='email-description'></span>
			</li>
			<li>
				<label for='phonenumber'>* 전화번호</label>
				<input type='text' name='phonenumber' id='phonenumber'> 
				<button type='button' name="phone_number_authenticate_btn" id="phone_number_authenticate_btn" onclick="javascript:click_authenticate_phonenumber()">인증번호 받기</button>
				<span id='phone-description'></span> 
			</li>
			<li>
				<label for='address'>주소</label>
				<input type='text' name='address' id='address' placeholder="우편번호" readonly> <button type="button" onclick="execPostCode()">우편번호 찾기</button>
				<div>
					<input type='text' name='address_detail1' id='address_detail1' value="" placeholder="도로명주소" readonly>
					<input type='text' name='address_detail2' id='address_detail2' value="" placeholder="상세 주소" maxlength='60'>
					<span id="address-description"></span>
				</div>
			</li>
			<li>
				<button type="button" onclick="javascript:submit_register_data()">가입하기</button>
			</li>
		</ul>
	</form>
	<c:if test="${result eq 0}">
		<script>
			alert("등록에 실패 하였습니다.");
		</script>
	</c:if>

	
</body>
</html>