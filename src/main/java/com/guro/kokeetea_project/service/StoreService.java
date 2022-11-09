package com.guro.kokeetea_project.service;

import com.guro.kokeetea_project.dto.StoreFormDTO;
import com.guro.kokeetea_project.dto.StoreInfoDTO;
import com.guro.kokeetea_project.entity.Store;
import com.guro.kokeetea_project.repository.StoreRepository;
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
public class StoreService {
    private final StoreRepository storeRepository;

    @Transactional(readOnly = true)
    public Page<StoreInfoDTO> list(Pageable pageable){
        List<Store> stores = storeRepository.listStore(pageable);
        Long totalCount = storeRepository.countStore();
        List<StoreInfoDTO> list = new ArrayList<>();

        for (Store store : stores){
            StoreInfoDTO dto = new StoreInfoDTO();
            dto.setId(store.getId());
            dto.setName(store.getName());
            dto.setLocation(store.getLocation());
            dto.setPhone(store.getPhone());
            dto.setEmail(store.getEmail());
            list.add(dto);
        }

        return new PageImpl<>(list, pageable, totalCount);
    }

    public Long create(StoreFormDTO storeFormDTO){
        Store store = new Store();
        store.setName(storeFormDTO.getName());
        store.setLocation(storeFormDTO.getLocation());
        store.setPhone(storeFormDTO.getPhone());
        store.setEmail(storeFormDTO.getEmail());
        storeRepository.save(store);

        return store.getId();
    }
}
