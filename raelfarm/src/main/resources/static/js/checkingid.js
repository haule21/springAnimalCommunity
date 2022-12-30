/**
 * 
 */

// 닉네임 정규식
let nire = /^[가-힣a-zA-Z0-9]{3,11}/; 
let idre = /^[a-zA-Z0-9]{6,16}$/; // 아이디와 패스워드가 적합한지 검사할 정규식
let adre = /^[가-힣a-zA-Z0-9]|[-_.,\(\)\[\]@\#$~`!]$/
let phre = /^[0-9]{10,11}$/;
let re2 = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i; //이메일 정규식

let idck = 0;
let pwck = 0;
let phck = 0;
let nick = 0;

var header = $("meta[name='_csrf_header']").attr('content');
var token = $("meta[name='_csrf']").attr('content');

var red = "#ff0000";
var blue = "#0982f0";

const user = { id : '', password : '', nickname : '',pwCheck : (pw) => chkPW(pw) };

function clickidcheck(){
    let userid =  $("#userid").val(); 
    if(userid == null){
		$("#userid").css({"border-color" : red });
		$("#userid-description").css({"color" : red});
		$("#userid-description").text("아이디를 입력해주세요.");
		$("#userid").focus();
		return false;
	}
    else if(userid.length < 6){
		$("#userid").css({"border-color" : red });
		$("#userid-description").css({"color" : red});
		$("#userid-description").text("아이디가 너무 짧습니다! 6자리 이상으로 작성해주세요.");
		$("#userid").focus();
		return false;
	}
	else if(!idre.test($("#userid").val())){
		$("#userid").css({"border-color" : red });
		$("#userid-description").css({"color" : red});
		$("#userid-description").text("아이디 형식에 맞게 입력해주세요.");
		$("#userid").focus();
		return false;
	}
    
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
				$("#userid").css({"border-color" : red });
				$("#userid-description").css({"color" : red});
				$("#userid-description").text("아이디가 존재합니다. 다른 아이디를 입력해주세요.");
				
            } else {
				$("#userid").css({"border-color" : blue });
				$("#userid-description").css({"color" : blue});
				$("#userid-description").text("사용가능한 아이디입니다.");
				
                //아이디가 중복하지 않으면  idck = 1 
                idck = 1;
                user.id = $("#userid").val();
                console.log("user.id :" + user.id + " idck :" + idck);
            }
        },
        error : function(request,status,error) {
            alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
        }
    });
}

function clicknicknamecheck(){
    let nickname =  $("#nickname").val(); 
    
    
    $.ajax({
        async: true,
        type : 'POST',
        data : nickname,
        url : "register_page/nicknamecheck",
        dataType : "json",
        contentType: "application/json",
        beforeSend: function(xhr){
        	xhr.setRequestHeader(header, token);
    	},
        success : function(data) {
            if (data.cnt > 0) {
                
                $("#nickname").css({"border-color" : red });
                $("#nickname-description").css({"color" : red});
                $("#nickname-description").text("이미 존재합니다.");
                return false;
            
            } else {
                //아이디가 중복하지 않으면  idck = 1 
                nick = 1;
                $("#nickname").css({"border-color" : blue });
                $("#nickname-description").text("사용 가능한 닉네임 입니다.");
				$("#nickname-description").css({"color" : blue});
                user.nickname = $("#nickname").val();
                return true;
            }
        },
        error : function(error) {
            
            alert("error : " + error);
        }
    });
}

function click_authenticate_phonenumber(){
	
	
    var phonenumber =  $("#phonenumber").val(); 
    if(phonenumber == "" || phonenumber == null || phonenumber.length != 11 || phonenumber == undefined){
		$("#phonenumber").css({"border-color" : red });
		$("#phone-description").css({"color" : red});
		$("#phone-description").text("휴대폰 형식에 맞게 입력해주세요.");
		return false;
	}
    
    $.ajax({
        async: true,
        type : 'POST',
        data : phonenumber,
        url : "/register_page/send-one",
        dataType : "json",
        contentType: "application/json",
        beforeSend: function(xhr){
        	xhr.setRequestHeader(header, token);
    	},
        success : function(data) {
			if(data.duplicate == "duplicate"){
				$("#phonenumber").css({"border-color" : red});
				$("#phone-description").css({"color" : red});
				$("#phone-description").text("이미 가입된 사용자 입니다.");
				return false;
			}
			else{
				$("#phonenumber").css({"border-color" : blue});
				$("#phone-description").css({"color" : blue});
				$("#phone-description").text("");
			}
			
			var txt1 = "<input type=\"text\" id=\"phone_authentication_num\"></input>";
			$("#phone_number_authenticate_btn").before(txt1);
            $("#phonenumber").attr("readonly", true);
            $("#phone_number_authenticate_btn").text("인증");
            $("#phone_number_authenticate_btn").attr("onclick","");
            
            $("#phone_number_authenticate_btn").click(
				function() {					
					if(data.authentication_number == null){
						$("#phone_authentication_num").css({"border-color" : red });
						$("#phone-description").css({"color" : red});
						$("#phone-description").text("인증에 실패하였습니다. 인증번호를 확인해주세요.(null)");
						return false;
					}
					else if(data.authentication_number != $("#phone_authentication_num").val()){
						$("#phone_authentication_num").css({"border-color" : red });
						$("#phone-description").css({"color" : red});
						$("#phone-description").text("인증에 실패하였습니다. 인증번호를 확인해주세요. (진짜번호틀림)");
						return false;
					}
					else if(data.authentication_number == $("#phone_authentication_num").val()){
						$("#phone_authentication_num").css({"border-color" : blue });
						$("#phone-description").css({"color" : blue});
						$("#phone-description").text("인증에 성공하였습니다!");
						$("#phone_number_authenticate_btn").text("인증완료");
						$("#phone_number_authenticate_btn").attr("disabled", true);
						$("#phone_authentication_num").remove();
						phck = 1;
						return true;
					}
				}
			);
        },
        error : function(error) {
            
            alert("error : " + error);
        }
    });
}



function submit_register_data(){
	if(idck == 0){
		$("#userid").css({"border-color" : red });
		$("#userid-description").css({"color" : red});
		$("#userid-description").text("아이디 중복 체크를 해주세요!");
		$("#userid").focus();
		return false;
	}
	else if(pwck == 0){
		$("#rechek-password").css({"border-color" : red });
		$("#recheck-description").css({"color" : red});
		$("#rechek-description").text("패스워드가 다릅니다.");
		$("#rechek-password").focus();
		return false;
	}
	else if(phck == 0){
		$("#phonenumber").css({"border-color" : red });
		$("#phone-description").css({"color" : red});
		$("#phone-description").text("휴대폰 인증을 진행하여 주세요.");
		$("#phonenumber").focus();
		return false;
	}
	else if(nick == 0){
		$("#nickname").css({"border-color" : red });
		$("#nickname-description").css({"color" : red});
		$("#nickname-description").text("닉네임을 확인해주세요.");
		$("#nickname").focus();
		return false;
	}
	else if($("#name").val().length < 1){
		$("#name").css({"border-color" : red });
		$("#name-description").css({"color" : red});
		$("#name-description").text("이름을 입력하여 주세요.");
		$("#name").focus();
		return false;
	}
	else if($("#userid").val().length < 6){
		$("#userid").css({"border-color" : red });
		$("#userid-description").css({"color" : red});
		$("#userid-description").text("아이디가 너무 짧습니다! 6자리 이상으로 작성해주세요.");
		$("#userid").focus();
		return false;
	}
	else if(!phre.test($("#phonenumber").val())){
		$("#phonenumber").css({"border-color" : red });
		$("#phone-description").css({"color" : red});
		$("#phone-description").text("휴대폰 형식에 맞게 입력해주세요.");
		$("#phonenumber").focus();
		return false;
	}
	else if(!idre.test($("#userid").val())){
		$("#userid").css({"border-color" : red });
		$("#userid-description").css({"color" : red});
		$("#userid-description").text("아이디 형식에 맞게 입력해주세요.");
		$("#userid").focus();
		return false;
	}
	else if(!nire.test($("#nickname").val())){
		$("#nickname").css({"border-color" : red });
		$("#nickname-description").css({"color" : red});
		$("#nickname-description").text("닉네임 형식에 맞게 입력해주세요.");
		$("#nickname").focus();
		return false;
	}
	else if(!re2.test($("#email").val()) || $("#email").val().length > 60 || $("#email").val().length < 1 ){
		$("#email").css({"border-color" : red });
		$("#email-description").css({"color" : red});
		$("#email-description").text("이메일 형식에 맞게 입력해주세요.");
		$("#email").focus();
		return false;
	}
	else if(!adre.test($("#address_detail2").val()) && $("#address_detail2").val().trim() != ""){
		$("#address_detail2").css({"border-color" : red });
		$("#address-description").css({"color" : red});
		$("#address-description").text("주소를 확인해주세요.");
		$("#address_detail2").focus();
		return false;
	}
	var pw = $("#password").val();
	if(chkPW(pw)){
		$("#register_form").submit();
		return true;
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
		$("#password").css({"border-color" : red});
		$("#password-description").css({"color" : red});
		$("#password-description").text("패스워드를 입력해주세요.");
		return false;
	}
	
	if(num < 0 || eng < 0 || spe < 0){
		$("#password").css({"border-color" : red });
		$("#password-description").css({"color" : red});
		$("#password-description").text("패스워드 형식에 맞춰서 입력해주세요.");
		return false;
	}
	else if(pw.length < 8 || pw.length > 20){
		$("#password").css({"border-color" : red });
		$("#password-description").css({"color" : red});
		$("#password-description").text("8자리 ~ 20자리 이내로 입력해주세요.");
  		return false;
	}
	else if(pw.search(/\s/) != -1){
		$("#password").css({"border-color" : red });
		$("#password-description").css({"color" : red});
		$("#password-description").text("비밀번호는 공백 없이 입력해주세요.");
  		return false;
	}
	else{
		$("#password").css({"border-color" : blue });
		$("#password-description").css({"color" : blue});
		$("#password-description").text("");
		user.password = pw;
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

