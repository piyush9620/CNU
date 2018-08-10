package com.cnu.assignment04.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Blob;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String username;

    private String fullname;

    private Integer mobile;

    private String email;

    private Blob password;

    @CreationTimestamp
    private Date created_at;

    @UpdateTimestamp
    private Date updated_at;

    @OneToMany
    @JoinColumn(name="user_id")
    private List<Address> addressList;

}

//+------------+------------------+------+-----+-------------------+-----------------------------+
//| Field      | Type             | Null | Key | Default           | Extra                       |
//+------------+------------------+------+-----+-------------------+-----------------------------+
//| id         | int(11) unsigned | NO   | PRI | NULL              | auto_increment              |
//| username   | varchar(45)      | NO   | UNI | NULL              |                             |
//| fullname   | varchar(100)     | NO   |     | NULL              |                             |
//| mobile     | int(10) unsigned | YES  | UNI | NULL              |                             |
//| email      | varchar(100)     | YES  | UNI | NULL              |                             |
//| password   | blob             | YES  |     | NULL              |                             |
//| created_at | datetime         | NO   |     | CURRENT_TIMESTAMP |                             |
//| updated_at | datetime         | NO   |     | CURRENT_TIMESTAMP | on update CURRENT_TIMESTAMP |
//+------------+------------------+------+-----+-------------------+-----------------------------+