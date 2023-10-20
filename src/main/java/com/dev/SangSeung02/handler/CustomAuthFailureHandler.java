package com.dev.SangSeung02.handler;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import lombok.SneakyThrows;

@Component
public class CustomAuthFailureHandler extends SimpleUrlAuthenticationFailureHandler{
	
	
	@SneakyThrows
	@Override
    public void onAuthenticationFailure(
    		HttpServletRequest request, 
    		HttpServletResponse response,
            AuthenticationException exception
            ) throws IOException, ServletException {
        
		String errorMessage = "잘못된 아이디 혹은 패스워드 입니다.";
        if (exception instanceof BadCredentialsException) {
            errorMessage = "PWER";
        } else if (exception instanceof InternalAuthenticationServiceException) {
            errorMessage = "InternalAuthenticationServiceException";
        } else if (exception instanceof UsernameNotFoundException) {
            errorMessage = "NONE";
        } else if (exception instanceof AuthenticationCredentialsNotFoundException) {
            errorMessage = "AuthenticationCredentialsNotFoundException";
        } else {
        	System.out.println(exception);
            errorMessage = "알 수 없는 이유로 로그인에 실패하였습니다 관리자에게 문의하세요.";
        }
        errorMessage = URLEncoder.encode(errorMessage, "UTF-8");
        setDefaultFailureUrl("/memberLogin?error=true&exception="+errorMessage);
 
        super.onAuthenticationFailure(request, response, exception);

//		  ObjectMapper mapper = new ObjectMapper();	//JSON 변경용
//        request.getParameter("memberId");
//        ResponseDataDTO responseDataDTO = new ResponseDataDTO();
//        System.out.println(exception.getMessage());
//        if(exception.getMessage().contains("not Certification")){
//            String memberKey = exception.getMessage().split("TT")[1];
//            Member param = new Member();
//            param.setMemberKey(memberKey);
//            Member memberCertification = memberDao.getMemberJoinCertification(param);
//            String parameter;
//            if(memberCertification == null){
//                parameter = String.format("%s**%s**%s", memberKey, "A", "A");
//            }else{
//                parameter = String.format("%s**%s**%s", memberCertification.getMemberKey(), memberCertification.getMemberEmail(), memberCertification.getFpNumber());
//            }
//            responseDataDTO.setItem(new AES256Util(myConfig.getJoinParameterKey()).encrypt(parameter));
//
//            responseDataDTO.setCode(ResponseDataCode.NOT_CERTIFICATION);
//            responseDataDTO.setStatus(ResponseDataStatus.ERROR);
//            responseDataDTO.setMessage("No Certification E-mail.");
//
//        }else if(exception.getMessage().contains("temporary email")){
//            responseDataDTO.setCode(ResponseDataCode.ERROR);
//            responseDataDTO.setStatus(ResponseDataStatus.ERROR);
//            responseDataDTO.setMessage(String.format("please use emails from %s",memberDao.getEnableEmailName()));
//
//        }else{
//            responseDataDTO.setCode(ResponseDataCode.ERROR);
//            responseDataDTO.setStatus(ResponseDataStatus.ERROR);
//            responseDataDTO.setMessage("ID or password do not match.");
//        }
//        responseDataDTO.setCode(ResponseDataCode.ERROR);
//        responseDataDTO.setStatus(ResponseDataStatus.ERROR);
//        responseDataDTO.setMessage("ID or password do not match.");
//
//        response.setCharacterEncoding("UTF-8");
//        response.setStatus(HttpServletResponse.SC_OK);
//        response.getWriter().print(mapper.writeValueAsString(responseDataDTO));
//        response.getWriter().flush();
		
		
		
    }
}
