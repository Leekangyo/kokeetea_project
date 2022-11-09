package com.guro.kokeetea_project.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guro.kokeetea_project.dto.IngredientInfoDTO;
import com.guro.kokeetea_project.dto.RequestFormDTO;
import com.guro.kokeetea_project.dto.RequestInfoDTO;
import com.guro.kokeetea_project.dto.StoreInfoDTO;
import com.guro.kokeetea_project.entity.Ingredient;
import com.guro.kokeetea_project.entity.Request;
import com.guro.kokeetea_project.entity.Store;
import com.guro.kokeetea_project.repository.IngredientRepository;
import com.guro.kokeetea_project.repository.RequestRepository;
import com.guro.kokeetea_project.repository.StoreRepository;
import com.guro.kokeetea_project.repository.WarehouseRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RequestService {
    private final RequestRepository requestRepository;
    private final IngredientRepository ingredientRepository;
    private final StoreRepository storeRepository;
    private final WarehouseRepository warehouseRepository;

    @Transactional(readOnly = true)
    public Page<RequestInfoDTO> list(Pageable pageable){
        List<Request> requests = requestRepository.listRequest(pageable);
        Long totalCount = requestRepository.countRequest();
        List<RequestInfoDTO> list = new ArrayList<>();

        for (Request request : requests){
            RequestInfoDTO dto = new RequestInfoDTO();
            dto.setId(request.getId());
            dto.setIngredientName(request.getIngredient().getName());
            dto.setAmount(request.getAmount());
            dto.setStoreName(request.getStore().getName());
            dto.setDate(request.getDate());
            if (request.getWarehouse() != null) {
                dto.setWarehouseName(request.getWarehouse().getName());
            }
            list.add(dto);
        }

        return new PageImpl<>(list, pageable, totalCount);
    }

    public Long create(RequestFormDTO requestFormDTO){
        Ingredient ingredient = ingredientRepository.findById(requestFormDTO.getIngredientId())
                .orElseThrow(EntityNotFoundException::new);
        Store store = storeRepository.findById(requestFormDTO.getStoreId())
                .orElseThrow(EntityNotFoundException::new);

        Request request = new Request();
        request.setIngredient(ingredient);
        request.setAmount(requestFormDTO.getAmount());
        request.setStore(store);
        requestRepository.save(request);

        return request.getId();
    }

    public List<IngredientInfoDTO> ingredients() {
        List<Ingredient> ingredients = ingredientRepository.findAll();
        List<IngredientInfoDTO> list = new ArrayList<>();

        for (Ingredient ingredient : ingredients) {
            IngredientInfoDTO dto = new IngredientInfoDTO();
            dto.setId(ingredient.getId());
            dto.setName(ingredient.getName());
            list.add(dto);
        }

        return list;
    }

    public List<StoreInfoDTO> stores() {
        List<Store> stores = storeRepository.findAll();
        List<StoreInfoDTO> list = new ArrayList<>();

        for (Store store : stores) {
            StoreInfoDTO dto = new StoreInfoDTO();
            dto.setId(store.getId());
            dto.setName(store.getName());
            list.add(dto);
        }

        return list;
    }
}
