package com.cnu.assignment04.repositories;

import com.cnu.assignment04.entities.Item;
import com.cnu.assignment04.entities.Restaurant;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete


public interface ItemRepository extends CrudRepository<Item, Long> {

}