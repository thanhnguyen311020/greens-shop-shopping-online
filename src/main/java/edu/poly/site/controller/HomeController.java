package edu.poly.site.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import edu.poly.entity.Product;
import edu.poly.service.ProductService;

@Controller
@RequestMapping({"greens","/"})
public class HomeController {
	
	@Autowired
	ProductService productService;
	
	
	@RequestMapping({"home",""})
	public String home(Model model) {
		
		Pageable pageable = PageRequest.of(	0, 8,Sort.by(Direction.DESC,"createDate"));
		Page<Product> newProduct = productService.findAll(pageable);
		model.addAttribute("newProduct", newProduct);
		
		Pageable pageable1 = PageRequest.of(0, 4,Sort.by(Direction.DESC,"price"));
		Page<Product> featuredProduct = productService.findAll(pageable1);
		model.addAttribute("featuredProduct", featuredProduct);
		
		return "site/layout/_home";
	}
	
//	@RequestMapping({"/admin","/admin/index"})
//	public ModelAndView admin(ModelMap model) {
//		model.addAttribute("model", "hello");
//		return new ModelAndView( "redirect:/admin/index.html#!/dashboard",model);
//	}
	
	@RequestMapping({"/admin","/admin/index"})
	public ModelAndView admin(ModelMap model) {
		model.addAttribute("message", "hello");
		return new ModelAndView( "/admin/index",model);
	}
//	
//	@ModelAttribute("newProduct")
//	public List<Product> newProduct(Model model){
//		
//		return;
//	}
}
