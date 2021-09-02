package edu.poly.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.poly.entity.Student;
import edu.poly.reponsitory.StudentReponsitory;
import edu.poly.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService{
	
	@Autowired
	private StudentReponsitory studentReponsitory;

	/**
	 * @param <S>
	 * @param entity
	 * @return
	 * @see org.springframework.data.repository.CrudRepository#save(java.lang.Object)
	 */
	@Override
	public <S extends Student> S save(S entity) {
		Optional<Student> st = studentReponsitory.findById(entity.getEmail());
		if(st.isPresent()) {
			entity.setMarks(st.get().getMarks()+entity.getMarks());
		}
		
		return studentReponsitory.save(entity);
	}

	/**
	 * @return
	 * @see org.springframework.data.jpa.repository.JpaRepository#findAll()
	 */
	@Override
	public List<Student> findAll() {
		return studentReponsitory.findAll();
	}

	/**
	 * @param id
	 * @see org.springframework.data.repository.CrudRepository#deleteById(java.lang.Object)
	 */
	@Override
	public void deleteById(String id) {
		studentReponsitory.deleteById(id);
	}

	/**
	 * @param ids
	 * @return
	 * @see org.springframework.data.jpa.repository.JpaRepository#findAllById(java.lang.Iterable)
	 */
	public List<Student> findAllById(Iterable<String> ids) {
		return studentReponsitory.findAllById(ids);
	}

	/**
	 * @param id
	 * @return
	 * @see org.springframework.data.repository.CrudRepository#findById(java.lang.Object)
	 */
	@Override
	public Optional<Student> findById(String id) {
		return studentReponsitory.findById(id);
	}
	
}
