package edu.poly.site.controller;



import java.io.UnsupportedEncodingException;
import java.security.Principal;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.security.auth.login.AccountNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.servlet.ModelAndView;

import edu.poly.Utility;
import edu.poly.entity.Account;
import edu.poly.service.AccountService;
import net.bytebuddy.utility.RandomString;


@Controller
public class AccountController {
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@GetMapping("greens/profile/{username}")
	public String editProfile(@PathVariable("username")String username,Model model) {
		Account account = accountService.findById(username).get();
		model.addAttribute("account",account);
		return "site/account/view";
	}
	
	@GetMapping("greens/forgotPassword")
	public String showForgotPasswordForm(Model model) {
		model.addAttribute("pageTitle", "Forgot Password");
		
		return "site/account/sendMail";
	}
	

	
	@PostMapping("greens/forgotPassword")
	public String processForgotPasswordForm( HttpServletRequest request, Model model)  {
		String email = request.getParameter("email");
		String token =RandomString.make(45);// chi dinh so ki tu ma hoa
		
		try {
			accountService.updateResetPasswordToken(token, email);
			
			String resetPasswordLink =Utility.getGreensURL(request)+"/greens/reset_password?token="+token;
			
			sendEmail(email, resetPasswordLink);
			
			model.addAttribute("message","We have sent a reset password link to your email. Please check");
			
		} catch (AccountNotFoundException e) {	
			model.addAttribute("error", e.getMessage());
		} catch (UnsupportedEncodingException | MessagingException e) {
			model.addAttribute("error", "Error while sending email");
		}
		
		return "site/account/sendMail";
	}

	private void sendEmail(String email, String resetPasswordLink) throws UnsupportedEncodingException, MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		
		helper.setFrom("ThanhND04094@fpt.edu.vn", "Greens Suport");
		helper.setTo(email);
		
		String subject =  "Here's the link to reset your password";
		String content = "<p>Hello, </p>"
				+ "<p> You have request to reset your password</p>"
				+ "<p>Click the link below to change your password</p>"
				+ "<p><b><a href=\""+resetPasswordLink +"\">Change my password </a></b></p>"
						+ "<p>Ignore this email if you do remember password, or you have nod made the request</p>";
		helper.setSubject(subject);
		helper.setText(content,true);
		mailSender.send(message);
	}
	
	@GetMapping("greens/reset_password")
	public String showResetpasswordForm(@Param(value="token")String token, Model model) {
		
		Account account = accountService.get(token);
		if(account == null) {
			model.addAttribute("message","Invalid token");
			return  "site/account/sendMail";
		}
		
		model.addAttribute("token", token);
		return "site/account/fogotPass";
	}
	
	
	@PostMapping("greens/reset_password")
	public String processResetPassword(Model model, HttpServletRequest request) {
		
		String token = request.getParameter("token");
		String password = request.getParameter("password");
		
		Account account = accountService.get(token);
		if(account == null) {
			model.addAttribute("message","Invalid token");
			return "message";
		}else {
			accountService.updatePassword(account, password);
			model.addAttribute("message","You have successfully changed your password");
			return "redirect:/greens/home";
		}
		
		
		
	}
	
	@GetMapping("greens/register")
	public String showRegisterForm(Model model) {
		Account account = new Account();
		model.addAttribute("account",account);
		return "register";
	}
	
	@PostMapping("greens/register")
	public ModelAndView register(ModelMap model,@Valid @ModelAttribute("account")Account account,BindingResult br,HttpServletRequest request) {
		if(br.hasErrors()) {
			return new ModelAndView("register",model);
		}
		
		
		
		
		
		accountService.save(account);
		model.addAttribute("message","Đăng Ký Thành Công");
		return new ModelAndView("forward:/greens/security/login/form",model);
	}
	
	@GetMapping("greens/changePass")
	public String showChangePassForm(Model model, Principal principal, Authentication authentication) {
		Account account = accountService.findById(principal.getName()).get();
		model.addAttribute("account",account);
//		System.out.println("Username"+ principal.getName());
//		System.out.println("Username Authen: "+ authentication.getName());
//		System.out.println("Account Pass"+ account.getPassword());
		return "site/account/changePass";
	}
	
	@PostMapping("/greens/changePass")
	public String changePass(Model model, HttpServletRequest request,Principal principal) {
		Account account = accountService.findById(principal.getName()).get();
		String oldPass = request.getParameter("oldPassword");
		String newPass = request.getParameter("password");
		System.out.println("Account Name = "+ account.getUsername());
		System.out.println("password old = "+account.getPassword());
		System.out.println(bCryptPasswordEncoder.matches(oldPass, account.getPassword()));
		if(account != null) {
			if(bCryptPasswordEncoder.matches(oldPass, account.getPassword())) {
				account.setPassword(newPass);
				accountService.save(account);
				model.addAttribute("message","Đổi mật khẩu thành công");
				return "redirect:/greens/security/login/form";
			}else {
				model.addAttribute("message", "Bạn Nhập Sai Mật Khẩu Cũ");
				return "site/account/changePass";
			}
			
		}
		
		return "forward:/greens/security/login/form";
	}
}
