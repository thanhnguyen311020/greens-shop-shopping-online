package edu.poly.reponsitory;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.poly.entity.Order;
import edu.poly.report.AmountReport;

@Repository
public interface OrderReponsitory extends JpaRepository<Order,Long>{

	@Query("select o from Order o where o.account.username like ?1 order by o.createDate desc")
	List<Order> findByUsername(String username);
	
	@Query("select o from Order o order by o.createDate desc")
	List<Order> findAllOrderByCreateDate();
	
	@Query("select sum(o.amount) from Order o where MONTH(o.createDate) = MONTH(GetDate()) and o.status=4")
    Double sumAmount(); 
	
	@Query("select new edu.poly.report.AmountReport(MONTH(o.createDate), sum(o.amount)) from Order o group by MONTH(o.createDate)")
	List<AmountReport> amountMonth();
	
	@Query("select sum(o.amount) from Order o where o.status = 4 and o.createDate  between ?1 and ?2 ")
	Double sumAmountOrderBetweenDate(Date start, Date end);

}
