package edu.poly.service.impl;


import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.poly.entity.Order;
import edu.poly.entity.OrderDetail;
import edu.poly.entity.Product;
import edu.poly.reponsitory.OrderDetailReponsitory;
import edu.poly.reponsitory.OrderReponsitory;
import edu.poly.reponsitory.ProductReponsitory;
import edu.poly.report.AmountReport;

import edu.poly.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{

	
	@Autowired
	private OrderReponsitory orderReponsitory;
	@Autowired
	private OrderDetailReponsitory orderdetailRe;
	@Autowired
	private ProductReponsitory productReponsitory;

	@Override
	public <S extends Order> S save(S entity) {
		return orderReponsitory.save(entity);
	}
	
	@Override
	public Order create(JsonNode orderData) {
		ObjectMapper mapper = new ObjectMapper();
		
		Order order = mapper.convertValue(orderData, Order.class);
		orderReponsitory.save(order);
		
		TypeReference<List<OrderDetail>> type = new TypeReference<List<OrderDetail>>() {};
		List<OrderDetail> details = mapper.convertValue(orderData.get("orderDetails"),type)
				.stream().peek(d -> d.setOrder(order)).collect(Collectors.toList());


				for(OrderDetail od : details){
					Product pd = productReponsitory.findById(od.getProduct().getId()).get();
					pd.setQuantity(pd.getQuantity()- od.getQuantity());
					productReponsitory.save(pd);
				}

		orderdetailRe.saveAll(details);
		
		return order;
		
	}

	@Override
	public Order cancelOrder(Order entity){
		
		
		List<OrderDetail> odList = orderdetailRe.findByOrderId(entity.getId());
		for(OrderDetail od: odList){
			Product pd = productReponsitory.findById(od.getProduct().getId()).get();
					pd.setQuantity(pd.getQuantity() + od.getQuantity());
		}
		entity.setStatus(0);
		return orderReponsitory.save(entity);
	} 

	@Override
	public Order updateStatusOrder(Order entity){
		Order od = orderReponsitory.findById(entity.getId()).get();
		if(od.getStatus() != 0 && od.getStatus() <4){
			entity.setStatus(od.getStatus()+1);
			return orderReponsitory.save(entity);
		}
		return null;
	}
	
	@Override
	public List<Order> findAll() {
		return orderReponsitory.findAll();
	}

	@Override
	public Optional<Order> findById(Long id) {
		return orderReponsitory.findById(id);
	}

	@Override
	public Double sumAmount() {
		return orderReponsitory.sumAmount();
	}

	@Override
	public long count() {
		return orderReponsitory.count();
	}

	@Override
	public void deleteById(Long id) {
		orderReponsitory.deleteById(id);
	}

	@Override
	public List<Order> findByUsername(String id) {
		// TODO Auto-generated method stub
		return orderReponsitory.findByUsername(id);
	}

	@Override
	public List<AmountReport> amountMonth() {
		return orderReponsitory.amountMonth();
	}

	/**
	 * @param start
	 * @param end
	 * @return
	 * @see edu.poly.reponsitory.OrderReponsitory#sumAmountOrderBetweenDate(java.util.Date, java.util.Date)
	 */
	@Override
	public Double sumAmountOrderBetweenDate(Date start, Date end) {
		return orderReponsitory.sumAmountOrderBetweenDate(start, end);
	}

	/**
	 * @return
	 * @see edu.poly.reponsitory.OrderReponsitory#findAllOrderByCreateDate()
	 */
	@Override
	public List<Order> findAllOrderByCreateDate() {
		return orderReponsitory.findAllOrderByCreateDate();
	}


	
	
}
