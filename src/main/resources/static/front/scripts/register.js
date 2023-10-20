
var dupCheck = false;
var phoneNumCheck = false;

var pswdChk = document.getElementById('passwordCheck');
var checkMsg = document.getElementById('checkMsg');
var username = document.getElementById('username');
var regType = /^[A-Za-z0-9]*$/;
var usernameCheck = document.getElementById('idCheck');
var phone = document.getElementById('phone')
var phoneCheck = document.getElementById('phoneCheck');

const idDupCheck = (target) =>{
	$.ajax({
		type:'post',
		url:'/memberDupCheck',
		async:false,
		data:{
			username:target.value,
		},
		success:function(result){
			if(result === 'success'){
				usernameCheck.innerText = ''
				dupCheck = true;
			}else if(result === 'fail'){
				dupCheck = false;
			}
		
		},
		error:function(request, status, error){
			alert('서버 에러 발생. 관리자에게 문의하세요.');
		}
		
	});
}


const autoHyphen = (target) => {
	target.value = target.value
		.replace(/[^0-9]/g, '')
		.replace(/^(\d{0,3})(\d{0,4})(\d{0,4})$/g, "$1-$2-$3").replace(/(\-{1,2})$/g, "");
		
		$.ajax({
		type:'post',
		url:'/memberPhoneCheck',
		async:false,
		data:{
			phone:target.value,
		},
		success:function(result){
			if(result === 'success'){
				phoneCheck.innerText = '';
				phoneNumCheck = true;
			}else if(result === 'fail'){
				phoneNumCheck = false;
			}
		
		},
		error:function(request, status, error){
			alert('서버 에러 발생. 관리자에게 문의하세요.');
		}
		
	});

}
	

function inCheck(){
	var pswd = document.getElementById('password').value;
	if(pswd!=pswdChk.value){
		pswdChk.focus();
		checkMsg.innerText = '# 비밀번호 확인을 정확하게 입력 해 주세요.';
		usernameCheck.innerText = ''
		phoneCheck.innerText = ''
		return false;
	}else if(!regType.test(username.value)){
		username.focus();
		usernameCheck.innerText = '# ID는 영문 혹은 숫자만 사용 해 주세요.'
		checkMsg.innerText = '';
		phoneCheck.innerText = ''
		return false;
	}else if(!dupCheck){
		username.focus();
		usernameCheck.innerText = '# 동일한 ID가 존재합니다. 다른 ID를 사용 해 주세요.'
		phoneCheck.innerText = ''
		checkMsg.innerText = '';
		return false;
	}else if(!phoneNumCheck){
		usernameCheck.innerText = ''
		checkMsg.innerText = '';
		phoneCheck.innerHTML = '# 동일한 휴대폰 번호로 가입되었습니다.'+ '<a href="/findID"> ID찾기</a>'+ '를 이용해 주세요.'
		return false;
	}else {
		usernameCheck.innerText = '';
		checkMsg.innerText = '';
		phoneCheck.innerText = ''
		return true;
	}
}














