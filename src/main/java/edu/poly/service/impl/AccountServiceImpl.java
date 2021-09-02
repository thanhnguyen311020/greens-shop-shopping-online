package edu.poly.service.impl;

import java.util.List;
import java.util.Optional;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import edu.poly.entity.Account;
import edu.poly.reponsitory.AccountReponsitory;
import edu.poly.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService{
	
	@Autowired
	private AccountReponsitory accountReponsitory;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public <S extends Account> S save(S entity) {
		Optional<Account> optExist = findById(entity.getUsername());
		if(optExist.isPresent()) {
			if(StringUtils.isEmpty(entity.getPassword())) {
				entity.setPassword(optExist.get().getPassword());
			}else {
				entity.setPassword(bCryptPasswordEncoder.encode(entity.getPassword()));
			}
		}else {
			entity.setPassword(bCryptPasswordEncoder.encode(entity.getPassword()));
		}
		
		
		return accountReponsitory.save(entity);
	}
	
	

	@Override
	public List<Account> findAll() {
		return accountReponsitory.findAll();
	}

	@Override
	public Optional<Account> findById(String id) {
		return accountReponsitory.findById(id);
	}

	@Override
	public <S extends Account> Page<S> findAll(Example<S> example, Pageable pageable) {
		return accountReponsitory.findAll(example, pageable);
	}

	@Override
	public long count() {
		return accountReponsitory.count(); 
	}

	@Override
	public void deleteById(String id) {
		accountReponsitory.deleteById(id);
	}
	
	@Override
	public void updateResetPasswordToken(String token, String email) throws AccountNotFoundException  {
		Account account = accountReponsitory.findByEmail(email);
		
		if(account != null) {
			account.setResetPasswordToken(token);
			accountReponsitory.save(account);
		}else {
			throw new AccountNotFoundException("Could not find any account with email");
		}
	}
	
	@Override
	public Account get(String resetPasswordToken) {
		return accountReponsitory.findByResetPasswordToken(resetPasswordToken);
	}
	
	@Override
	public void updatePassword(Account account, String newPassword) {
		BCryptPasswordEncoder passwordEncoder= new BCryptPasswordEncoder();
		String encodePassword = passwordEncoder.encode(newPassword);
		
		account.setPassword(encodePassword);
		account.setResetPasswordToken(null);
		
		accountReponsitory.save(account);
		
	}



	/**
	 * @param email
	 * @return
	 * @see edu.poly.reponsitory.AccountReponsitory#findByEmail(java.lang.String)
	 */
	@Override
	public Account findByEmail(String email) {
		return accountReponsitory.findByEmail(email);
	}



	/**
	 * @return
	 * @see edu.poly.reponsitory.AccountReponsitory#getAdministrators()
	 */
	@Override
	public List<Account> getAdministrators() {
		return accountReponsitory.getAdministrators();
	}
	
	
}
