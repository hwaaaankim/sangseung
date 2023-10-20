package com.dev.SangSeung02.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

import com.dev.SangSeung02.handler.AjaxAuthenticationEntryPoint;
import com.dev.SangSeung02.handler.CustomAuthFailureHandler;
import com.dev.SangSeung02.handler.CustomAuthenticationProvider;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private CustomAuthFailureHandler customFailureHandler;
	
	@Autowired
	private CustomAuthenticationProvider customAuthenticationProvider;
	
//	@Autowired
//	private PasswordEncoding passwordEncoding;
	
//	@Autowired
//	public void configureGlobalSecurity(AuthenticationManagerBuilder authentication) throws Exception {
//	    authentication.userDetailsService(userDetailsServiceBean());
//	    //authentication.authenticationProvider(authenticationProvider());
//	}

//	@Bean(name="securityEncoder")
//	public PasswordEncoder passwordEncoder() {
//	    return new BCryptPasswordEncoder();
//	}
//	
	@Bean
    public SpringSecurityDialect springSecurityDialect(){
        return new SpringSecurityDialect();
    }
    
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		System.out.println("webconfig - configure");
        auth.authenticationProvider(customAuthenticationProvider);
        /* auth
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
         */
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
				.antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
//				.antMatchers("/my/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MEMBER')")
				.antMatchers("/my/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_MEMBER")
				.antMatchers("/**").permitAll()
			.anyRequest().authenticated()
			.and()
		.formLogin()
			.loginPage("/memberLoginForm")
			.loginProcessingUrl("/memberLogin")
			.failureHandler(customFailureHandler)
			.defaultSuccessUrl("/")
			.and()
		.logout()
			.logoutUrl("/logout")
			.logoutSuccessUrl("/")
			.deleteCookies("JSESSIONID")
			.invalidateHttpSession(true)
			.permitAll();
		http.exceptionHandling()
			.authenticationEntryPoint(new AjaxAuthenticationEntryPoint("/memberLoginForm"));
//		http.sessionManagement()
////		        .maximumSessions(3)
////		        .maxSessionsPreventsLogin(true)
////		        .expiredUrl("/expired");
				
	}	
	
//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) 
//	  throws Exception {
//	    auth.jdbcAuthentication()
//	      .dataSource(dataSource)
//	      .passwordEncoder(passwordEncoder)
//	      .usersByUsernameQuery("select MEMBER_USERNAME, MEMBER_PASSWORD, MEMBER_ENABLED "
//	        + "from member "
//	        + "where MEMBER_USERNAME = ?")
//	      .authoritiesByUsernameQuery("select u.MEMBER_USERNAME, r.ROLE_NAME "
//	        + "from member_role ur inner join member u on ur.MEMBER_ID = u.MEMBER_ID "
//	        + "inner join role r on ur.ROLE_ID = r.ROLE_ID "
//	        + "where u.MEMBER_USERNAME = ?");
//	}
	
}
