package edu.poly.service;

import java.util.List;
import java.util.Optional;

import edu.poly.entity.Role;

public interface RoleService {

	void deleteById(String id);

	long count();

	Optional<Role> findById(String id);

	List<Role> findAll();

	<S extends Role> S save(S entity);

}
