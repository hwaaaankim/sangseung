package com.dev.SangSeung02.handler;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.dev.SangSeung02.model.Member;
import com.dev.SangSeung02.model.MemberAccount;
import com.dev.SangSeung02.service.MemberService;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private MemberService userDetailsService;

    
//    @Autowired
//    private PasswordEncoding passwordEncoding;
    @Bean(name="AuthEncoder")
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    	
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        System.out.println("Authenticate");
        System.out.println("PW : " + password);
        System.out.println("ID : " + username);
        MemberAccount memberAccount = (MemberAccount) userDetailsService.loadUserByUsername(username);
        System.out.println(memberAccount.toString());
        
        if (memberAccount.getMember()==null) {
        	System.out.println("Account is None");
            throw new BadCredentialsException("Account is None");
        }
        Member member = memberAccount.getMember();
        System.out.println(member.toString());
        if (!passwordEncoder().matches(password, member.getPassword())) {
        	System.out.println("Password not match");
            throw new BadCredentialsException("Password not match");
        }
        
//        if (!passwordEncoder().matches(SHA256Util.getEncrypt(password,""), memberAccount.getPassword())) {
//        	System.out.println("Account is None");
//            throw new BadCredentialsException("Account is None");
//        }
//        Member member = memberAccount.getMember();
//        member.setPassword("");
//
//        if (!passwordEncoder().matches(SHA256Util.getEncrypt(password,""), memberAccount.getPassword())) {
//        	System.out.println("Password not match");
//            throw new BadCredentialsException("Password not match");
//        }

//        Member member = new Member();
//        member.setMemberKey(account.getMemberKey());
//        Member detail = memberService.getMemberInfoForMemberKey(member);



//        if(detail!=null && !StringUtils.isBlank(detail.getMemberEmail())){
//            String emailName = detail.getMemberEmail().split("@")[1];
//            if(memberDao.checkEnableEmailName(emailName.trim()) < 1){
//                throw new DisabledException("temporary email");
//            }
//
//        }

        if (!member.getEnabled()) {
        	System.out.println("not CertificationTT");
            throw new DisabledException("not CertificationTT"+member.getUsername());
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
        		member,
                null,
                memberAccount.getAuthorities());
        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
