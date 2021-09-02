package edu.poly.site.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.poly.entity.Category;
import edu.poly.entity.Product;
import edu.poly.service.CategoryService;
import edu.poly.service.ProductService;

@Controller
@RequestMapping("greens")
public class ProductController {

	@Autowired
	CategoryService categorySevice;
	@Autowired
	ProductService productService;
//	
//	@RequestMapping("product")
//	public String list(Model model) {
//		java.util.List<Product> list = productService.findAll();
//		model.addAttribute("products", list);	
//		return "site/product/list";
//	}
//	
//	@ModelAttribute("categories")
//	public java.util.List<Category> getCategories(){
//		java.util.List<Category> list = categorySevice.findAll();
//		return list;
//	}

	@RequestMapping("produc1t")
	public String search(Model model, @RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "ids", required = false) String ids, @RequestParam(name = "page") Optional<Integer> page,
			@RequestParam(name = "size") Optional<Integer> size, @ModelAttribute("product") Product product) {

		Product productDto = new Product();
		model.addAttribute("product", productDto);

		int currentPage = page.orElse(1);
		int pageSize = size.orElse(6);

		Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("name"));
		Page<Product> resultPage = null;

		if (StringUtils.hasText(name) || StringUtils.hasText(ids)) {
			resultPage = productService.findByKeywords(name, ids, pageable);
			model.addAttribute("name", name);
		} else {
			resultPage = productService.findAll(pageable);
		}

		// so trang duoc hien hi
		int totalPage = resultPage.getTotalPages();
		if (totalPage > 0) {
			int start = Math.max(1, totalPage - 2);
			int end = Math.min(currentPage + 2, totalPage);

			if (totalPage > 5) {
				if (end == totalPage)
					start = end - 5;
				else if (start == 1)
					end = start + 5;
			}
			List<Integer> pageNumbers = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
			System.out.println("Page:" + pageNumbers.size());
			model.addAttribute("pageNumbers", pageNumbers);
		}

		model.addAttribute("name", name);
		model.addAttribute("ids",ids);
		model.addAttribute("productPage", resultPage);

		return "site/product/list";
	}

	@GetMapping("products")
	public String productView(ModelMap model,
			@RequestParam("page") Optional<Integer> page,
			@RequestParam(name = "name", required = false) String name) {

		int currentPage = page.orElse(1);
		int pageSize = 9;

		Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
		Page<Product> resultPage = null;
		if (StringUtils.hasText(name)) {
			resultPage = productService.findByKeywords(name, pageable);
			model.addAttribute("name", name);
		} else {
			resultPage = productService.findAll(pageable);
		}

		int totalPage = resultPage.getTotalPages();
		if (totalPage > 0) {
			int start = Math.max(1, totalPage - 2);
			int end = Math.min(currentPage + 2, totalPage);

			if (totalPage > 5) {
				if (end == totalPage)
					start = end - 5;
				else if (start == 1)
					end = start + 5;
			}

			List<Integer> pageNumbers = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());

			model.addAttribute("pageNumbers", pageNumbers);
		}

		model.addAttribute("name", name);
		model.addAttribute("productPage", resultPage);

		return "site/product/list";
	}
	
	@RequestMapping("product")
	public String fildAll(Model model
			/*@Param("keyword")String keyword*/) {
	
//		List<Product> list = service.findAll(keyword);
//		model.addAttribute("products",list);
//		model.addAttribute("keyword", keyword);
//		System.out.println("Size" +list.size());
		
		// pageable
		String keyword = null;
		
		return listByPage(model, 1, "name", "asc", keyword);
	}
	
	@GetMapping("product/page/{pageNumber}")
	public String listByPage(Model model,
			@PathVariable("pageNumber") int currentPage,
			@Param("sortField")String sortField,
			@Param("sortDir")String sortDir,
			@Param("keyword")String keyword) {
		
		Page<Product> page = productService.listAll(currentPage, sortField, sortDir, keyword);
		
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
		
		return "site/product/list";
	}

	@RequestMapping("/product/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id) {
		Optional<Product> product = productService.findById(id);
		model.addAttribute("product", product.get());
		Pageable pageable = PageRequest.of(0, 4,Sort.by(Direction.ASC,"name"));
		Page<Product> productLikeCategory = productService.findProductByCategory(product.get().getCategory().getId(), pageable);
		model.addAttribute("productC", productLikeCategory);
		return "site/product/detail";
	}
}
