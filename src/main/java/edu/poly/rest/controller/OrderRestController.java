package edu.poly.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;

import edu.poly.entity.Order;
import edu.poly.service.OrderService;

@CrossOrigin("*")
@RestController
@RequestMapping("greens/rest/order")
public class OrderRestController {

	@Autowired
	OrderService orderService;
	
	@PostMapping()
	public Order create(@RequestBody JsonNode order) {
		return orderService.create(order);
	}
	
	@GetMapping()
	public List<Order> list(){
		return orderService.findAllOrderByCreateDate();
	}
	
	@PutMapping("{id}")
	public Order update(@PathVariable("id")Long id, @RequestBody Order order) {
		return orderService.save(order);
	}
	
	@DeleteMapping("{id}")
	public void delete(@PathVariable("id")Long id) {
		orderService.deleteById(id);
	}

	@PutMapping("/updateStatus/{id}")
	public Order updateStatusOrder(@PathVariable("id") Long id,@RequestBody Order order){
		return orderService.updateStatusOrder(order);
	}
}
