package com.cnu.assignment04.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String address;

    private String landmark;

    private String latitude;

    private String longitude;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}

//+-------------+------------------+------+-----+---------+----------------+
//| Field       | Type             | Null | Key | Default | Extra          |
//+-------------+------------------+------+-----+---------+----------------+
//| id          | int(11) unsigned | NO   | PRI | NULL    | auto_increment |
//| address     | varchar(255)     | NO   |     | NULL    |                |
//| landmark    | varchar(255)     | YES  |     | NULL    |                |
//| location_id | int(11) unsigned | NO   | MUL | NULL    |                |
//| latitude    | decimal(10,8)    | YES  |     | NULL    |                |
//| longitude   | decimal(11,8)    | YES  |     | NULL    |                |
//| user_id     | int(11) unsigned | YES  |     | NULL    |                |
//+-------------+------------------+------+-----+---------+----------------+