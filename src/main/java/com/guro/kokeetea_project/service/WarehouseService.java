package com.guro.kokeetea_project.service;

import com.guro.kokeetea_project.dto.CurrentStockInfoDTO;
import com.guro.kokeetea_project.dto.WarehouseInfoDTO;
import com.guro.kokeetea_project.entity.*;
import com.guro.kokeetea_project.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class WarehouseService {
    private final CurrentStockRepository currentStockRepository;
    private final WarehouseRepository warehouseRepository;
    private final IngredientRepository ingredientRepository;
    private final StoreRepository storeRepository;
    private final RequestRepository requestRepository;

    @Transactional(readOnly = true)
    public Page<WarehouseInfoDTO> list(Pageable pageable) {
        List<Warehouse> warehouses = warehouseRepository.listWarehouse(pageable);
        List<WarehouseInfoDTO> warehouseInfoDTOList = new ArrayList<>();

        for (Warehouse warehouse : warehouses){
            WarehouseInfoDTO warehouseInfoDTO = new WarehouseInfoDTO();
            warehouseInfoDTO.setId(warehouse.getId());
            warehouseInfoDTO.setName(warehouse.getName());

            List<CurrentStock> currentStocks = currentStockRepository.listValidCurrentStock(warehouse);
            List<CurrentStockInfoDTO> currentStockInfoDTOList = new ArrayList<>();

            for(CurrentStock currentStock : currentStocks) {
                CurrentStockInfoDTO currentStockInfoDTO = new CurrentStockInfoDTO();
                currentStockInfoDTO.setId(currentStock.getId());
                currentStockInfoDTO.setIngredientName(currentStock.getIngredient().getName());
                currentStockInfoDTO.setAmount(currentStock.getAmount());
                currentStockInfoDTOList.add(currentStockInfoDTO);
            }

            warehouseInfoDTO.setCurrentStockInfoDTOList(currentStockInfoDTOList);
            warehouseInfoDTOList.add(warehouseInfoDTO);
        }

        Long totalCount = warehouseRepository.countWarehouse();

        return new PageImpl<>(warehouseInfoDTOList, pageable, totalCount);
    }

    public void TEMP_test() {
        currentStockRepository.deleteAll();
        warehouseRepository.deleteAll();
        requestRepository.deleteAll();
        storeRepository.deleteAll();
        ingredientRepository.deleteAll();

        Ingredient i1 = new Ingredient();
        i1.setName("초콜릿");
        i1.setPrice(1400L);
        i1.setIsValid(true);
        ingredientRepository.save(i1);

        Ingredient i2 = new Ingredient();
        i2.setName("우유");
        i2.setPrice(1200L);
        i2.setIsValid(true);
        ingredientRepository.save(i2);

        Ingredient i3 = new Ingredient();
        i3.setName("커피 원두");
        i3.setPrice(900L);
        i3.setIsValid(true);
        ingredientRepository.save(i3);

        Store s1 = new Store();
        s1.setName("메인1호점");
        s1.setLocation("뉴욕");
        s1.setPhone("01022223333");
        s1.setEmail("honey@gmail.com");
        s1.setIsValid(true);
        storeRepository.save(s1);

        Store s2 = new Store();
        s2.setName("싱클레어2호점");
        s2.setLocation("시카고");
        s2.setPhone("01023457111");
        s2.setEmail("jaja123@gmail.com");
        s2.setIsValid(true);
        storeRepository.save(s2);

        Request r1 = new Request();
        r1.setIngredient(i1);
        r1.setAmount((long) Math.floor(Math.random()*50));
        r1.setStore(s1);
        requestRepository.save(r1);

        Request r2 = new Request();
        r2.setIngredient(i3);
        r2.setAmount((long) Math.floor(Math.random()*50));
        r2.setStore(s1);
        requestRepository.save(r2);

        Request r3 = new Request();
        r3.setIngredient(i2);
        r3.setAmount((long) Math.floor(Math.random()*50));
        r3.setStore(s2);
        requestRepository.save(r3);

        Warehouse w1 = new Warehouse();
        w1.setName("뉴욕1창고");
        w1.setLocation("뉴욕");
        w1.setPhone("01023234545");
        w1.setEmail("griffin123@gmail.com");
        w1.setIsValid(true);
        warehouseRepository.save(w1);

        CurrentStock cs1 = new CurrentStock();
        cs1.setIngredient(i1);
        cs1.setWarehouse(w1);
        cs1.setAmount((long) Math.floor(Math.random()*500));
        currentStockRepository.save(cs1);

        CurrentStock cs2 = new CurrentStock();
        cs2.setIngredient(i2);
        cs2.setWarehouse(w1);
        cs2.setAmount((long) Math.floor(Math.random()*500));
        currentStockRepository.save(cs2);

        Warehouse w2 = new Warehouse();
        w2.setName("뉴욕3창고");
        w2.setLocation("뉴욕");
        w2.setPhone("01023885555");
        w2.setEmail("haha@gmail.com");
        w2.setIsValid(true);
        warehouseRepository.save(w2);

        CurrentStock cs3 = new CurrentStock();
        cs3.setIngredient(i1);
        cs3.setWarehouse(w2);
        cs3.setAmount(110L);
        currentStockRepository.save(cs3);

        CurrentStock cs4 = new CurrentStock();
        cs4.setIngredient(i3);
        cs4.setWarehouse(w2);
        cs4.setAmount(222L);
        currentStockRepository.save(cs4);
    }
}
