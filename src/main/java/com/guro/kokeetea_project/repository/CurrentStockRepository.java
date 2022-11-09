package com.guro.kokeetea_project.repository;

import com.guro.kokeetea_project.entity.CurrentStock;
import com.guro.kokeetea_project.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CurrentStockRepository extends JpaRepository<CurrentStock, Long> {
    List<CurrentStock> findByWarehouse(Warehouse warehouse);
}
