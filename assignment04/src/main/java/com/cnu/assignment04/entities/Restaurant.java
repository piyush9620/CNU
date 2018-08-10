package com.cnu.assignment04.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    @NotNull
    @ColumnDefault("1")
    private Boolean is_open;

    private List<String> cuisineNames;

    public void setName(String name) {
        this.name = name;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCuisines(Set<Cuisine> cuisines) {
        this.cuisines = cuisines;
    }

    public List<String> getCuisineNames() {
        return cuisineNames;
    }

    @JsonProperty("cuisines")
    public void setCuisineNames(List<String> cuisineNames) {
        this.cuisineNames = cuisineNames;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public void setIs_open(Boolean is_open) {
        this.is_open = is_open;
    }

    public void addCuisine(Cuisine cuisine) {
        cuisines.add(cuisine);
        cuisine.getRestaurants().add(this);
    }

    public Set<Cuisine> getCuisines() {
        return cuisines;
    }
}
