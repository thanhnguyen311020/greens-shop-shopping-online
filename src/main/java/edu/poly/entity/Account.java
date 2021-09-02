package edu.poly.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "Accounts")
public class Account  implements Serializable {
    @Id
    @NotEmpty()
    @Length(min = 1)
    String username;
    @Length(min=1)
    String password;
    @NotBlank
    String fullname;
    @NotBlank
    @Email
    String email;
    String phone;
    String photo;
    @Temporal(TemporalType.DATE)
    @Column(name = "Createdate")
    Date createDate = new Date();
    
    @Column(name = "reset_password_token")
     String resetPasswordToken;
    
    @JsonIgnore
    @OneToMany(mappedBy = "account")
    List<Order> orders;

    @JsonIgnore
    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    List<Authority> authorities;
}
