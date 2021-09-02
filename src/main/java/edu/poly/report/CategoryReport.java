package edu.poly.report;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import edu.poly.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CategoryReport implements Serializable{
	
	@Id
	Category category;
	Long quantity;
	Double grossRevenue;
}
