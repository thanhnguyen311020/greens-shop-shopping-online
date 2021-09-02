package edu.poly.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.poly.entity.Account;
import edu.poly.service.AccountService;

@CrossOrigin("*")
@RestController
@RequestMapping("/greens/rest/account")
public class AccountRestController {
	
	@Autowired
	AccountService accountService;
	
	@GetMapping()
	public List<Account> getAccounts(){
		return accountService.findAll();
	}
	
	@PostMapping()
	public Account createAccount(@RequestBody Account account) {
		return accountService.save(account);
	}
	
	@PutMapping("{username}")
	public Account update(@PathVariable("username")String username,@RequestBody Account account) {
		return accountService.save(account);
	}
	@DeleteMapping("{username}")
	public void delete(@PathVariable("username")String username) {
		accountService.deleteById(username);
	}
	
	@GetMapping("{username}")
	public Account findById(@PathVariable("username")String username) {
		return accountService.findById(username).get();
	}
	
	// get account by role = admin
	
	@GetMapping("role")
	public List<Account> getAccounts(@RequestParam("admin")Optional<Boolean> admin){
		if(admin.orElse(false)) {
			return accountService.getAdministrators();
		}
		return accountService.findAll();
	}
}
