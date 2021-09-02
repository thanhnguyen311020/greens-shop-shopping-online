package edu.poly.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import edu.poly.entity.Product;

public interface ProductService {

	void deleteById(Integer id);

	long count();

	Optional<Product> findById(Integer id);

	List<Product> findAll();

	<S extends Product> S save(S entity);

	Page<Product> findByKeywords(String name, String id, Pageable pageable);

	Page<Product> findAll(Pageable pageable);

	Float getPrice(Integer id);

	Page<Product> findByKeywords(String name, Pageable pageable);

	/**
	 * @param keyword
	 * @return
	 * @see edu.poly.reponsitory.ProductReponsitory#findAllKeyWord(java.lang.String)
	 */
	List<Product> findAll(String keyword);

	/**
	 * @param keyword
	 * @param pageable
	 * @return
	 * @see edu.poly.reponsitory.ProductReponsitory#findAll(java.lang.String, org.springframework.data.domain.Pageable)
	 */
	Page<Product> listAll(int pageNumber, String sortField, String sortDir, String keyword);

	/**
	 * @param id
	 * @param pageable
	 * @return
	 * @see edu.poly.reponsitory.ProductReponsitory#findProductByCategory(java.lang.String, org.springframework.data.domain.Pageable)
	 */
	Page<Product> findProductByCategory(String id, Pageable pageable);

	Product saveByExcel(Product product);

}
