package com.cnu.assignment04.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    private Float price;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
}
