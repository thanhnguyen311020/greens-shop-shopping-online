package edu.poly.report;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import edu.poly.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ReportProduct implements Serializable{
	
	@Id
	private Product product;
	private Long sumOrder;
}
