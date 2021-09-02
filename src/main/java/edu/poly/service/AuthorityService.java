package edu.poly.service;

import java.util.List;
import java.util.Optional;

import edu.poly.entity.Authority;

public interface AuthorityService {

	void deleteById(Integer id);

	long count();

	Optional<Authority> findById(Integer id);

	List<Authority> findAll();

	<S extends Authority> S save(S entity);


	List<Authority> findAuthoritiesOfAdministrators();

}
