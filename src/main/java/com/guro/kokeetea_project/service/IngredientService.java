package com.guro.kokeetea_project.service;

import com.guro.kokeetea_project.dto.*;
import com.guro.kokeetea_project.entity.Ingredient;
import com.guro.kokeetea_project.repository.IngredientRepository;
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
public class IngredientService {
    private final IngredientRepository ingredientRepository;

    @Transactional(readOnly = true)
    public Page<IngredientInfoDTO> list(Pageable pageable){
        List<Ingredient> ingredients = ingredientRepository.listIngredient(pageable);
        Long totalCount = ingredientRepository.countIngredient();
        List<IngredientInfoDTO> list = new ArrayList<>();

        for (Ingredient ingredient : ingredients){
            IngredientInfoDTO dto = new IngredientInfoDTO();
            dto.setId(ingredient.getId());
            dto.setName(ingredient.getName());
            dto.setPrice(ingredient.getPrice());
            list.add(dto);
        }

        return new PageImpl<>(list, pageable, totalCount);
    }

    public Long create(IngredientFormDTO ingredientFormDTO){
        Ingredient ingredient = new Ingredient();
        ingredient.setName(ingredientFormDTO.getName());
        ingredient.setPrice(ingredientFormDTO.getPrice());
        ingredientRepository.save(ingredient);

        return ingredient.getId();
    }
}
