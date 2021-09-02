package edu.poly.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.poly.entity.Account;
import edu.poly.entity.Authority;
import edu.poly.reponsitory.AccountReponsitory;
import edu.poly.reponsitory.AuthorityReponsitory;
import edu.poly.service.AuthorityService;

@Service
public class AuthorityServiceImpl implements AuthorityService{
	@Autowired
	private AuthorityReponsitory authorityReponsitory;
	
	@Autowired
	private AccountReponsitory  accountDao;

	@Override
	public <S extends Authority> S save(S entity) {
		return authorityReponsitory.save(entity);
	}

	@Override
	public List<Authority> findAll() {
		return authorityReponsitory.findAll();
	}

	@Override
	public Optional<Authority> findById(Integer id) {
		return authorityReponsitory.findById(id);
	}

	@Override
	public long count() {
		return authorityReponsitory.count();
	}

	@Override
	public void deleteById(Integer id) {
		authorityReponsitory.deleteById(id);
	}


	@Override
	public List<Authority> findAuthoritiesOfAdministrators() {
		List<Account> accounts = accountDao.getAdministrators();
		return authorityReponsitory.authoritiesOf(accounts);
	}
	
	
}
