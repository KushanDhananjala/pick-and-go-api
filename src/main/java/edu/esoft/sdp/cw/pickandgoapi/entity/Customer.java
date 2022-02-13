package edu.esoft.sdp.cw.pickandgoapi.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "customer")
public class Customer extends Auditable<Long> implements Serializable {

    @Id
    private String userName;
    private String customerName;
    private String email;
    private int mobileNo;
    private String address;
    private String gender;
    private int age;

}
