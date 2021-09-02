package edu.poly.reponsitory;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.poly.entity.Product;

@Repository
public interface ProductReponsitory  extends JpaRepository<Product, Integer>{
	
	@Query("select p from Product p where p.name like %?1% or p.category.id like %?2%")
	Page<Product> findByKeywords(String name, String id,Pageable pageable);
	
	@Query("select p from Product p where p.name like %?1% ")
	Page<Product> findByKeywords(String name, Pageable pageable);
	
	@Query("select p.price from Product p where p.id = ?1")
	Float getPrice(Integer id);
	
	//Find product by CreateDdate
//	@Query("select TOP 1  p FROM Product p order by p.createDate desc")
//	List<Product> findTop8NewProduct();
	
	@Query("SELECT p FROM Product p where p.category.id = ?1")
	Page<Product> findProductByCategory(String id, Pageable pageable);
	
	//Filter and Search
	//@Query("SELECT p FROM Product p WHERE p.name like %?1% OR p.category.name like %?1%")
	@Query("SELECT p FROM Product p WHERE "
			+ " CONCAT(p.name,' ', convert(nvarchar,p.price)) like %?1%")
	public List<Product> findAll(String keyword);
	
	
	@Query("SELECT p FROM Product p WHERE "
			+ "CONCAT( p.name,', ', p.category.name) LIKE %?1%")
	public Page<Product> findAll(String keyword, Pageable pageable);
}
