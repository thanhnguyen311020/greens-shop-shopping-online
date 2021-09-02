package edu.poly.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.poly.entity.Authority;
import edu.poly.service.AuthorityService;

@CrossOrigin("*")
@RestController()
@RequestMapping("greens/rest/authorities")
public class AuthoritiesRestController {

	@Autowired
	AuthorityService authorityService;
	
	@GetMapping("ok")
	public List<Authority> findAll(@RequestParam("admin")Optional<Boolean>admin){
		if(admin.orElse(false)) {
			return authorityService.findAuthoritiesOfAdministrators();
			
		}
		return authorityService.findAll();
	}
	
	@PostMapping
	public Authority post(@RequestBody Authority authority) {
		return authorityService.save(authority);
	}
	
	@DeleteMapping("{id}")
	public void delete(@PathVariable("id")Integer id) {
		authorityService.deleteById(id);
	}
}
