package com.guro.kokeetea_project.repository;

import com.guro.kokeetea_project.entity.CurrentStock;
import com.guro.kokeetea_project.entity.Ingredient;
import com.guro.kokeetea_project.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CurrentStockRepository extends JpaRepository<CurrentStock, Long> {
    @Query("select cs from CurrentStock cs where cs.warehouse = :warehouse and cs.ingredient.isValid = true")
    List<CurrentStock> listValidCurrentStock(@Param("warehouse") Warehouse warehouse);

    List<CurrentStock> findByIngredient(Ingredient ingredient);
    void deleteByIngredient(Ingredient ingredient);
}
