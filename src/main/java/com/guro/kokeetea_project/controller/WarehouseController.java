package com.guro.kokeetea_project.controller;

import com.guro.kokeetea_project.dto.WarehouseInfoDTO;
import com.guro.kokeetea_project.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class WarehouseController {
    private final WarehouseService warehouseService;

    @GetMapping(value = "/warehouse/test")
    public String TEMP_test( ){
        warehouseService.TEMP_test();

        return "redirect:/warehouse/list";
    }

    @GetMapping(value = {"/warehouse/list","/warehouse/list/{page}"})
    public String listWarehouse(@PathVariable("page") Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.orElse(1)-1, 10);
        Page<WarehouseInfoDTO> warehouseList = warehouseService.list(pageable);

        model.addAttribute("warehouses", warehouseList);
        model.addAttribute("maxPage", 5);

        return "warehouse/list";
    }

    @PostMapping(value = {"/warehouse/list/refresh","/warehouse/list/refresh/{page}"})
    public String refreshWarehouse(@PathVariable("page") Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.orElse(1)-1, 10);
        Page<WarehouseInfoDTO> warehouseList = warehouseService.list(pageable);
        model.addAttribute("warehouses", warehouseList);
        model.addAttribute("maxPage", 5);
        return "warehouse/list :: #warehouse-data";
    }
}
