package edu.poly.reponsitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.poly.entity.Account;
@Repository
public interface AccountReponsitory extends JpaRepository<Account, String>{
	
	@Query("SELECT a from Account a WHERE a.email = ?1")
	public Account findByEmail(String email);
	
	public Account findByResetPasswordToken(String token);
	
	@Query("SELECT DISTINCT ar.account FROM Authority ar WHERE ar.role.id IN ('DIRE','STAF') ")
	public List<Account> getAdministrators();
}
