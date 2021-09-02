package edu.poly.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import edu.poly.entity.Account;
import edu.poly.entity.Authority;
import edu.poly.entity.Role;


public class UserService {

	@Autowired
	AccountService accountService;
	@Autowired
	AuthorityService authorityService;
	@Autowired
	BCryptPasswordEncoder pe;


	public void loginFromOAuth2(OAuth2AuthenticationToken oauth2) {
		String email = oauth2.getPrincipal().getAttribute("name");
		String password = Long.toHexString(System.currentTimeMillis());
		Account opt = accountService.findByEmail(email);
		if(opt != null) {
			UserDetails user = User.withUsername(email).password(pe.encode(password)).roles("GUEST").build();
			Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(auth);
		}else {
			Authority au = new Authority();
			Role role = new Role();
			
			Account ac = new Account();
			
			ac.setUsername(email);
			ac.setPassword(password);
			ac.setEmail(email);
			ac.setPhoto("");
			ac.setFullname("Auth2");
			au.setAccount(ac);
			role.setId("CUST");
			au.setRole(role);
			
			accountService.save(ac);
			authorityService.save(au);
			
			UserDetails user = User.withUsername(email).password(password).roles("CUST").build();
			Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(auth);
		}
		
		
	}

}
