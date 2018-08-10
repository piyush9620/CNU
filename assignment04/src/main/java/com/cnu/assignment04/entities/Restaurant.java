package com.cnu.assignment04.entities;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "restaurants")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    @ColumnDefault("0")
    private Float rating;

    @NotNull
    @Size(max = 255)
    private String city;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "restaurant_cuisines",
            joinColumns = { @JoinColumn(name = "cuisine_id") },
            inverseJoinColumns = { @JoinColumn(name = "restaurant_id") })
    private Set<Cuisine> cuisines = new HashSet<>();

    @NotNull
    @Column(name="latitude", columnDefinition="Decimal(10,8)")
    private Float latitude;

    @NotNull
    @Column(name="longitude", columnDefinition="Decimal(11,8)")
    private Float longitude;

    @ColumnDefault("1")
    private Boolean is_open;
}

//{
//        “id”: 12,
//        “name”: “Flying Spaghetti Monster”,
//        “cuisines”: [“italian”, “continental”],
//        “city”: “Wadiya”,
//        “latitude”: 8.121212,
//        “longitude”: 5.35239,
//        “rating”: 8.1,
//        “is_open”: true
//        }
