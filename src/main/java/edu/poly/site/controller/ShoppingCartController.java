package edu.poly.site.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("greens")
public class ShoppingCartController {
	@RequestMapping("/cart/view")
	public String view() {
		return "site/cart/view";
	}
}
