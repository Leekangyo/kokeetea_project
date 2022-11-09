package com.guro.kokeetea_project.controller;

import com.guro.kokeetea_project.dto.IngredientFormDTO;
import com.guro.kokeetea_project.dto.IngredientInfoDTO;
import com.guro.kokeetea_project.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class IngredientController {
    private final IngredientService ingredientService;

    @GetMapping(value = {"/ingredient/list","/ingredient/list/{page}"})
    public String listIngredient(@PathVariable("page") Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.orElse(0), 10);
        Page<IngredientInfoDTO> ingredientList = ingredientService.list(pageable);

        model.addAttribute("ingredients", ingredientList);
        model.addAttribute("maxPage", 5);

        return "ingredient/list";
    }

    @GetMapping(value = "/ingredient/create")
    public String createIngredient(Model model){
        model.addAttribute("ingredientFormDTO", new IngredientFormDTO());
        return "ingredient/create";
    }

    @PostMapping(value = "/ingredient/create")
    public String createIngredientPost(@Valid IngredientFormDTO ingredientFormDTO, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            return "ingredient/create";
        }

        try {
            ingredientService.create(ingredientFormDTO);
        } catch (Exception e){
            model.addAttribute("errorMessage", "재료 등록 중 에러가 발생하였습니다.");
            return "ingredient/create";
        }
        return "redirect:/ingredient/list";
    }
}
