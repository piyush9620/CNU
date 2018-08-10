package com.cnu.assignment04.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String locality;

    private String district;

    private String state;

    private String country;

    private String pincode;

    @OneToMany
    @JoinColumn(name = "location_id")
    private List<Address> addresses;
}

//+----------+------------------+------+-----+---------+----------------+
//| Field    | Type             | Null | Key | Default | Extra          |
//+----------+------------------+------+-----+---------+----------------+
//| id       | int(11) unsigned | NO   | PRI | NULL    | auto_increment |
//| locality | varchar(255)     | YES  |     | NULL    |                |
//| district | varchar(255)     | NO   |     | NULL    |                |
//| state    | varchar(255)     | NO   |     | NULL    |                |
//| country  | varchar(255)     | NO   |     | NULL    |                |
//| pincode  | varchar(11)      | NO   | MUL | NULL    |                |
//+----------+------------------+------+-----+---------+----------------+