package edu.poly.service;

import java.util.List;
import java.util.Optional;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import edu.poly.entity.Account;

public interface AccountService {

	void deleteById(String id);

	long count();

	<S extends Account> Page<S> findAll(Example<S> example, Pageable pageable);

	Optional<Account> findById(String id);

	List<Account> findAll();

	<S extends Account> S save(S entity);

	void updatePassword(Account account, String newPassword);

	Account get(String resetPasswordToken);

	void updateResetPasswordToken(String token, String email) throws AccountNotFoundException;

	/**
	 * @return
	 * @see edu.poly.reponsitory.AccountReponsitory#getAdministrators()
	 */
	List<Account> getAdministrators();

	/**
	 * @param email
	 * @return
	 * @see edu.poly.reponsitory.AccountReponsitory#findByEmail(java.lang.String)
	 */
	Account findByEmail(String email);

}
