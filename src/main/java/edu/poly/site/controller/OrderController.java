package edu.poly.site.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.poly.entity.Order;
import edu.poly.entity.OrderDetail;
import edu.poly.entity.Product;
import edu.poly.service.OrderDetailService;
import edu.poly.service.OrderService;
import edu.poly.service.ProductService;

@Controller
@RequestMapping("greens/order")
public class OrderController {
	
	@Autowired
	OrderService orderService;
	@Autowired 
	OrderDetailService orderDetailService;
	@Autowired 
	ProductService productService;

	@RequestMapping("checkout")
	public String checkout() {
		return "site/order/checkout";
	}
	
	@RequestMapping("purchaseSuccess")
	public String purchaseSuccess() {
		return "site/order/purchaseSucess";
	}
	
	@RequestMapping("{username}")
	public String list(Model model,@PathVariable("username")String username) {
		model.addAttribute("orders",orderService.findByUsername(username));
		
		return "site/order/list";
	}
	
	@RequestMapping("detail/{id}")
	public String detail(@ModelAttribute("id")Long id,Model model) {
		model.addAttribute("order",orderService.findById(id).get());
		return "site/order/detail";
	}

	@RequestMapping("cancelOrder/{id}")
	public String cancelOrder(@PathVariable("id")Long id, Model model){
		Order order = orderService.findById(id).get();
		List<OrderDetail> details = orderDetailService.findByOrderId(id);
		for(OrderDetail od : details){
			Product pd = productService.findById(od.getProduct().getId()).get();
			pd.setQuantity(pd.getQuantity()+od.getQuantity());
			productService.save(pd);
		}
		order.setStatus(0);
		orderService.save(order);
		return "redirect:/greens/order/"+order.getAccount().getUsername();
	}
	
	
}
