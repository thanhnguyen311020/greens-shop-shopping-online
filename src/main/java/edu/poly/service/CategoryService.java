package edu.poly.service;

import java.util.List;
import java.util.Optional;

import edu.poly.entity.Category;

public interface CategoryService {

	void deleteById(String id);

	Optional<Category> findById(String id);

	List<Category> findAll();

	<S extends Category> S save(S entity);

}
