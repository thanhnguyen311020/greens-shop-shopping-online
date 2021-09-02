package edu.poly.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.poly.entity.Category;
import edu.poly.reponsitory.CategoryReponsitory;
import edu.poly.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{

	
	@Autowired
	private CategoryReponsitory categoryReponsitory;

	@Override
	public <S extends Category> S save(S entity) {
		return categoryReponsitory.save(entity);
	}

	@Override
	public List<Category> findAll() {
		return categoryReponsitory.findAll();
	}

	@Override
	public Optional<Category> findById(String id) {
		return categoryReponsitory.findById(id);
	}

	@Override
	public void deleteById(String id) {
		categoryReponsitory.deleteById(id);
	}
	
	
}
