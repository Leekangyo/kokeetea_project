package com.guro.kokeetea_project.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.guro.kokeetea_project.entity.Ingredient;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    @Query("select i from Ingredient i")
    List<Ingredient> listIngredient(Pageable pageable);

    @Query("select count(i) from Ingredient i")
    Long countIngredient();
}
