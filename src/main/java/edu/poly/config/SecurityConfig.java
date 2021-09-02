package edu.poly.config;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import edu.poly.entity.Account;
import edu.poly.service.AccountService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	AccountService accountService;
	@Autowired
	BCryptPasswordEncoder pe;

	// Cung cấp nguồn dữ liệu đăng nhập
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(username -> { 	 
			try {
				Account user = accountService.findById(username).get();
				String passwrod = ( user.getPassword());
				
				
//				System.out.println("password"+pe.encode(user.getPassword()));
				System.out.println("password: "+passwrod);
				String[] roles = user.getAuthorities().stream().map(er -> er.getRole().getId())
						.collect(Collectors.toList()).toArray(new String[0]);
				return User.withUsername(username).password(passwrod).roles(roles).build();
			} catch (NoSuchElementException e) {
				throw new UsernameNotFoundException(username + "not found");
			}
		});
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
			.antMatchers("/greens/order/**")
			.authenticated()
			.antMatchers("/greens/admin/**","/admin/**").hasAnyRole("STAF", "DIRE")
			.antMatchers("/greens/rest/authorities/**","http://localhost:8080/greens/admin#!/authority").hasRole("DIRE")
			.anyRequest().permitAll();
		http.formLogin()
			.loginPage("/greens/security/login/form")
			.loginProcessingUrl("/greens/security/login")// địa chỉ
			.defaultSuccessUrl("/greens/home", false)
			.failureUrl("/greens/security/login/error");
		
		http.rememberMe()
		.tokenValiditySeconds(8640);

		http.exceptionHandling()
		.accessDeniedPage("/greens/security/login/unauthoried");

		http.logout()
			.logoutUrl("/greens/security/logout")// link logoff
			.logoutSuccessUrl("/greens/security/logoff/success");
		
		
		 // OAuth2-Đăng nhập từ mạng xã hội
//     http.oauth2Login().loginPage("/greens/security/login/form").defaultSuccessUrl("/greens/home", false).failureUrl("/greens/security/login/error")
//     .authorizationEndpoint().baseUri("/oauth2/authorization");

	}

	@Bean
	public BCryptPasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	} 
	
//	Cho phép truy xuất REST API từ bên ngoài (domain khác)
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(HttpMethod.OPTIONS,"/**");
	}
}
