package edu.poly.reponsitory;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.poly.entity.OrderDetail;
import edu.poly.report.CategoryReport;
import edu.poly.report.ReportOderDate;
import edu.poly.report.ReportProduct;

@Repository
public interface OrderDetailReponsitory extends JpaRepository<OrderDetail, Long>{
	
	
	@Query("select od from OrderDetail od where od.order.id = ?1 ")
	List<OrderDetail> findByOrderId(Long id);
	
	@Query("SELECT new ReportProduct(od.product, SUM(od.quantity)) from OrderDetail od GROUP BY od.product.id order by count(od) desc")
	List<ReportProduct> findAllByTop();
	
	@Query("Select new ReportOderDate(od.id,od.quantity,od.price,od.order,od.product,od.order.account) from OrderDetail od where od.order.status =4 and od.order.createDate between ?1  and  ?2")
	List<ReportOderDate> findOrderDetailBetweenDate(Date start, Date end);
	
	@Query("select new CategoryReport(od.product.category, sum(od.quantity), sum(od.price)) from OrderDetail od group by od.product.category")
	List<CategoryReport> revenueByCategory();
}
 