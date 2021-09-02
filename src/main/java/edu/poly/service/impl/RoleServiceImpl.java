package edu.poly.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.poly.entity.Role;
import edu.poly.reponsitory.RoleReponsitory;
import edu.poly.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
	private RoleReponsitory reponsitory;

	@Override
	public <S extends Role> S save(S entity) {
		return reponsitory.save(entity);
	}

	@Override
	public List<Role> findAll() {
		return reponsitory.findAll();
	}

	@Override
	public Optional<Role> findById(String id) {
		return reponsitory.findById(id);
	}

	@Override
	public long count() {
		return reponsitory.count();
	}

	@Override
	public void deleteById(String id) {
		reponsitory.deleteById(id);
	}
	
}
