/**
 * 
 */
 
let re = /^[a-zA-Z0-9]{6,16}$/; // 아이디와 패스워드가 적합한지 검사할 정규식
let re2 = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i; //이메일 정규식

let idck = 0;
let pwck = 0;

var header = $("meta[name='_csrf_header']").attr('content');
var token = $("meta[name='_csrf']").attr('content');

const user = { id : '', pwCheck : (pw) => chkPW(pw) };

function clickidcheck(){
    let userid =  $("#userid").val(); 
    
    
    $.ajax({
        async: true,
        type : 'POST',
        data : userid,
        url : "register_page/idcheck",
        dataType : "json",
        contentType: "application/json",
        beforeSend: function(xhr){
        	xhr.setRequestHeader(header, token);
    	},
        success : function(data) {
            if (data.cnt > 0) {
                
                alert("아이디가 존재합니다. 다른 아이디를 입력해주세요.");
                $("#userid").focus();
                
            
            } else {
                alert("사용가능한 아이디입니다.");
                $("#password").focus();
                //아이디가 중복하지 않으면  idck = 1 
                idck = 1;
                user.id = $("#userid").val();
                console.log("user.id :" + user.id + " idck :" + idck);
            }
        },
        error : function(error) {
            
            alert("error : " + error);
        }
    });
}

function submit_register_data(){
	if(idck == 0){
		alter('아이디 중복 체크를 해주세요!');
		$("#userid").focus();
		return false;
	}
	else if(pwck == 0){
		alter('패스워드가 다릅니다. 다시 한번 입력해주세요.');
		return false;
	}
	else if($("#userid").val().length < 6){
		alter('아이디가 너무 짧습니다! 6자리 이상으로 작성해주세요.');
		$("#userid").focus();
		return false;
	}
	var pw = $("#password").val();
	if(chkPw(pw)){
		var registerform = document.getElementById('register_form');
		registerform.action = 'register_page/register.do'
		registerform.method = 'POST'
		registerform.submit();
	}
	else{
		return false;
	}
	
	
}

function chkPW(pw){
	
	console.log("CHKPW 실행 됐다. :" + pw);
	var num = pw?.search(/[0-9]/g);
	var eng = pw?.search(/[a-z]/ig);
	var spe = pw?.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);
	
	if(num == null || eng == null || spe == null){
		return false;
	}
	
	if(num < 0 || eng < 0 || spe < 0){
		alert("영문,숫자, 특수문자를 혼합하여 입력해주세요.");
		return false;
	}
	else if(pw.length < 8 || pw.length > 20){
		alert("8자리 ~ 20자리 이내로 입력해주세요.");
  		return false;
	}
	else if(pw.search(/\s/) != -1){
		alert("비밀번호는 공백 없이 입력해주세요.");
  		return false;
	}
	else{
		return true;
	}
}

function chkFocus(ele){
	if(document.activeElement == ele){
		
	}
	else{
		ele.focus();
	}
}

