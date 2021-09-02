package edu.poly.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import edu.poly.entity.OrderDetail;
import edu.poly.report.CategoryReport;
import edu.poly.report.ReportOderDate;
import edu.poly.report.ReportProduct;

public interface OrderDetailService {

	void deleteById(Long id);

	long count();

	Optional<OrderDetail> findById(Long id);

	List<OrderDetail> findAll();

	<S extends OrderDetail> S save(S entity);

	List<OrderDetail> findByOrderId(Long id);

	List<ReportProduct> findAllByTop();

	List<ReportOderDate> findOrderDetailBetweenDate(Date start, Date end);

	List<CategoryReport> revenueByCategory();

}
