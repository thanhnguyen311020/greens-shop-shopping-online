package edu.poly.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.poly.entity.Category;

@Repository
public interface CategoryReponsitory extends JpaRepository<Category, String>{

}
