package edu.poly.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import edu.poly.entity.Product;
import edu.poly.reponsitory.ProductReponsitory;
import edu.poly.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	private ProductReponsitory productReponsitory;

	@Override
	public <S extends Product> S save(S entity) {
		return productReponsitory.save(entity);
	}

	@Override
	public Page<Product> findAll(Pageable pageable) {
		return productReponsitory.findAll(pageable);
	}

	@Override
	public List<Product> findAll() {
		return productReponsitory.findAll();
	}

	@Override
	public Optional<Product> findById(Integer id) {
		return productReponsitory.findById(id);
	}

	/**
	 * @param keyword
	 * @return
	 * @see edu.poly.reponsitory.ProductReponsitory#findAllKeyWord(java.lang.String)
	 */
	@Override
	public List<Product> findAll(String keyword) {
		if(keyword == null) {
			return productReponsitory.findAll();
		}
		return productReponsitory.findAll(keyword);
	}

	@Override
	public long count() {
		return productReponsitory.count();
	}

	@Override
	public void deleteById(Integer id) {
		productReponsitory.deleteById(id);
	}

	@Override
	public Page<Product> findByKeywords(String name, String id, Pageable pageable) {
		return productReponsitory.findByKeywords(name, id, pageable);
	}

	@Override
	public Float getPrice(Integer id) {
		// TODO Auto-generated method stub
		return productReponsitory.getPrice(id);
	}

	@Override
	public Page<Product> findByKeywords(String name, Pageable pageable) {
		return productReponsitory.findByKeywords(name, pageable);
	}

	/**
	 * @param keyword
	 * @param pageable
	 * @return
	 * @see edu.poly.reponsitory.ProductReponsitory#findAll(java.lang.String, org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<Product> listAll(int pageNumber, String sortField, String sortDir,
				String keyword){
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc")? sort.ascending() : sort.descending();
		
		Pageable pageable = PageRequest.of(pageNumber -1, 9, sort);
		
		if(keyword != null) {
			return productReponsitory.findAll(keyword,pageable);
		}
		
		return productReponsitory.findAll(pageable);
	}

	/**
	 * @param id
	 * @param pageable
	 * @return
	 * @see edu.poly.reponsitory.ProductReponsitory#findProductByCategory(java.lang.String, org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<Product> findProductByCategory(String id, Pageable pageable) {
		return productReponsitory.findProductByCategory(id, pageable);
	}
	
	
//	Save by Excel
	
	@Override
	public Product saveByExcel(Product product) {
//		Optional<Product> pd = productReponsitory.findById(product.getId());
//		if(pd.isPresent()) {
//			product.setQuantity(pd.get().getQuantity()+product.getQuantity());
//		}
		
		return productReponsitory.save(product);
	}
	
	
}
