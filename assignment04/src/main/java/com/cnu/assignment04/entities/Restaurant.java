package com.cnu.assignment04.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "restaurants")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private Float rating;

    private Integer review_count;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany
    @JoinColumn(name = "restaurant_id")
    private List<Item> items;

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
//| address_id   | int(11) unsigned | YES  | MUL | NULL              |                             |
//| owner_id     | int(11) unsigned | YES  | MUL | NULL              |                             |
//| rating       | float            | YES  |     | 0                 |                             |
//| review_count | int(11)          | NO   |     | 0                 |                             |
//| created_at   | datetime         | YES  |     | CURRENT_TIMESTAMP |                             |
//| updated_at   | datetime         | YES  |     | CURRENT_TIMESTAMP | on update CURRENT_TIMESTAMP |
//+--------------+------------------+------+-----+-------------------+-----------------------------+