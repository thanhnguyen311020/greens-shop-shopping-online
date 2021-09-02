package edu.poly.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.poly.entity.Role;

@Repository
public interface RoleReponsitory extends JpaRepository<Role,String >{

}
