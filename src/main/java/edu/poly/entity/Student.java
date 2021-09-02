package edu.poly.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "Students")
public class Student implements Serializable {
    @Id
    String email;
    String fullname;
    Double Marks;
    Boolean gender;
    String country;
}
