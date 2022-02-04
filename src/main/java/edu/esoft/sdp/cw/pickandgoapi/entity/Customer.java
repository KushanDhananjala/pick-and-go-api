package edu.esoft.sdp.cw.pickandgoapi.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "customer")
public class Customer {

    @Id
    private String userName;
    private String customerName;
    private String email;
    private int mobileNo;
    private String address;
    private String gender;
    private int age;

}
