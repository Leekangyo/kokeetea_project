package com.guro.kokeetea_project.repository;

import java.util.List;

import com.guro.kokeetea_project.entity.Ingredient;
import com.guro.kokeetea_project.entity.Store;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.guro.kokeetea_project.entity.Request;

public interface RequestRepository extends JpaRepository<Request, Long> {
    @Query("select r from Request r " +
            "order by r.date desc")
    List<Request> listRequest(Pageable pageable);

    @Query("select count(r) from Request r ")
    Long countRequest();

    List<Request> findByIngredient(Ingredient ingredient);
    void deleteByIngredient(Ingredient ingredient);

    void deleteByStore(Store store);
}
