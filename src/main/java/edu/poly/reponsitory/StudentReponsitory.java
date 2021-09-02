package edu.poly.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.poly.entity.Student;

@Repository
public interface StudentReponsitory extends JpaRepository<Student, String>{

}
