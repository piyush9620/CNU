package com.cnu.assignment04.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private String description;

    private Float rating;

    private Float price;

    @ManyToOne
    @JoinColumn(name = "currency_id")
    private Currency currency;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    private Boolean veg;

    private String pincode;

    @CreationTimestamp
    private Date created_at;

    @UpdateTimestamp
    private Date updated_at;
}


//+--------------+------------------+------+-----+-------------------+-----------------------------+
//| Field        | Type             | Null | Key | Default           | Extra                       |
//+--------------+------------------+------+-----+-------------------+-----------------------------+
//| id           | int(11) unsigned | NO   | PRI | NULL              | auto_increment              |
//| name         | varchar(255)     | NO   |     | NULL              |                             |
//| description  | text             | YES  |     | NULL              |                             |
//| type_id      | int(11) unsigned | YES  | MUL | NULL              |                             |
//| restraunt_id | int(11) unsigned | NO   | MUL | NULL              |                             |
//| currency_id  | int(11) unsigned | NO   | MUL | NULL              |                             |
//| price        | float unsigned   | YES  |     | NULL              |                             |
//| rating       | float unsigned   | NO   |     | 0                 |                             |
//| veg          | tinyint(4)       | NO   |     | NULL              |                             |
//| created_at   | datetime         | YES  |     | CURRENT_TIMESTAMP |                             |
//| updated_at   | datetime         | YES  |     | CURRENT_TIMESTAMP | on update CURRENT_TIMESTAMP |
//+--------------+------------------+------+-----+-------------------+-----------------------------+