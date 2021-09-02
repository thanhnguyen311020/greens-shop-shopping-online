package edu.poly.site.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.poly.entity.Product;
import edu.poly.service.ProductService;

@Controller
@RequestMapping("demoFilterAndSearch")
public class FilterAndSeachController {
	
	@Autowired
	ProductService service;
	
	@RequestMapping()
	public String fildAll(Model model
			/*@Param("keyword")String keyword*/) {
	
//		List<Product> list = service.findAll(keyword);
//		model.addAttribute("products",list);
//		model.addAttribute("keyword", keyword);
//		System.out.println("Size" +list.size());
		
		// pageable
		String keyword = null;
		
		return listByPage(model, 2, "name", "asc", keyword);
	}
	
	@GetMapping("/page/{pageNumber}")
	public String listByPage(Model model,
			@PathVariable("pageNumber") int currentPage,
			@Param("sortField")String sortField,
			@Param("sortDir")String sortDir,
			@Param("keyword")String keyword) {
		
		Page<Product> page = service.listAll(currentPage, sortField, sortDir, keyword);
		
		long totalItems = page.getTotalElements();
		int totalPages = page.getTotalPages();
		
		List<Product> listProducts = page.getContent();
		
		
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("products", listProducts);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("keyword", keyword);
		
		String reverseSortDir = sortDir.equals("asc")?"desc":"asc";
		model.addAttribute("reverseSortDir", reverseSortDir);
		
		System.out.println("List" +listProducts.size());
		
		return "site/demo/demoFillerAndSearch";
	}
}
