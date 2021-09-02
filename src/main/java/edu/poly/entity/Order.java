package edu.poly.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "Orders")
public class Order  implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String address;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Createdate")
    Date createDate = new Date();
    @Column(name="phone")
    String phone;
    @Column(name ="amount")
    Float amount;
    Integer status =1;
    
    @ManyToOne
    @JoinColumn(name = "Username")
        Account account;

    @JsonIgnore
    @OneToMany(mappedBy = "order")
    List<OrderDetail> orderDetails;
}
