package com.cnu.assignment04.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "currency")
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

}

//+-------+------------------+------+-----+---------+----------------+
//| Field | Type             | Null | Key | Default | Extra          |
//+-------+------------------+------+-----+---------+----------------+
//| id    | int(10) unsigned | NO   | PRI | NULL    | auto_increment |
//| name  | varchar(45)      | NO   |     | NULL    |                |
//+-------+------------------+------+-----+---------+----------------+