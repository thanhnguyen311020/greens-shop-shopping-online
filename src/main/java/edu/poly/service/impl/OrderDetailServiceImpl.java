package edu.poly.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.poly.entity.OrderDetail;
import edu.poly.reponsitory.OrderDetailReponsitory;
import edu.poly.report.CategoryReport;
import edu.poly.report.ReportOderDate;
import edu.poly.report.ReportProduct;
import edu.poly.service.OrderDetailService;

@Service
public class OrderDetailServiceImpl implements OrderDetailService{

	
	@Autowired
	OrderDetailReponsitory oderDetailReponsitory;

	@Override
	public <S extends OrderDetail> S save(S entity) {
		return oderDetailReponsitory.save(entity);
	}

	@Override
	public List<OrderDetail> findAll() {
		return oderDetailReponsitory.findAll();
	}

	@Override
	public Optional<OrderDetail> findById(Long id) {
		return oderDetailReponsitory.findById(id);
	}

	@Override
	public long count() {
		return oderDetailReponsitory.count();
	}

	@Override
	public void deleteById(Long id) {
		oderDetailReponsitory.deleteById(id);
	}

	@Override
	public List<OrderDetail> findByOrderId(Long username) {
		
		return oderDetailReponsitory.findByOrderId(username);
	}

	@Override
	public List<ReportProduct> findAllByTop() {
		return oderDetailReponsitory.findAllByTop();
	}

	@Override
	public List<ReportOderDate> findOrderDetailBetweenDate(Date start, Date end) {
		return oderDetailReponsitory.findOrderDetailBetweenDate(start, end);
	}

	@Override
	public List<CategoryReport> revenueByCategory() {
		return oderDetailReponsitory.revenueByCategory();
	}
	
}
