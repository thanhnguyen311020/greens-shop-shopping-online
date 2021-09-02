package edu.poly.site.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("greens/security")
public class SecurityController {
	
	@RequestMapping("login/form")
	public String loginForm(Model model) {
		model.addAttribute("message","Vui lòng đăng nhập");
		return "/login";
	}
	
	@RequestMapping("login/success")
	public String success(Model model) {
		model.addAttribute("message","Đăng Nhập thành công");
		return "redirect:/greens/home";
	}
	
	@RequestMapping("login/error")
	public String error(Model model) {
		model.addAttribute("message","Sai thông tin đăng nhập");
		return "/login";
	}
	
	@RequestMapping("login/unauthoried")
	public String unauthoried(Model model) {
		model.addAttribute("message","Ban Khồn có quyền truy xuất");
		return "/403";
	}
	
	@RequestMapping("logoff/success")
	public String logoff(Model model) {
		model.addAttribute("message","Ban Khồn có quyền truy xuất");
		return "redirect:/greens/home";
	}
	
}
