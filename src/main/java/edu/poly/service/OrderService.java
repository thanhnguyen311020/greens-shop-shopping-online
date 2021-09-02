package edu.poly.service;


import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.JsonNode;

import edu.poly.entity.Order;
import edu.poly.report.AmountReport;


public interface OrderService {

	void deleteById(Long id); 

	long count();

	Optional<Order> findById(Long id);

	List<Order> findAll();

	<S extends Order> S save(S entity);

	Order create(JsonNode orderData);

	List<Order> findByUsername(String username);

	Double sumAmount();

	List<AmountReport> amountMonth();

	Order cancelOrder(Order entity);

	Order updateStatusOrder(Order entity);

	/**
	 * @param start
	 * @param end
	 * @return
	 * @see edu.poly.reponsitory.OrderReponsitory#sumAmountOrderBetweenDate(java.util.Date, java.util.Date)
	 */
	Double sumAmountOrderBetweenDate(Date start, Date end);

	/**
	 * @return
	 * @see edu.poly.reponsitory.OrderReponsitory#findAllOrderByCreateDate()
	 */
	List<Order> findAllOrderByCreateDate();
}
