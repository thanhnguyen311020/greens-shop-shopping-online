package edu.poly.report;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import edu.poly.entity.Account;
import edu.poly.entity.Order;
import edu.poly.entity.OrderDetail;
import edu.poly.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ReportOderDate implements Serializable{
	
	@Id
	Long id;
	Integer quantity;
	Double price;
	Order order;
	Product product;
	Account account;
	
}
