package com.guro.kokeetea_project.controller;

import com.guro.kokeetea_project.dto.IngredientFormDTO;
import com.guro.kokeetea_project.dto.IngredientInfoDTO;
import com.guro.kokeetea_project.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class IngredientController {
    private final IngredientService ingredientService;

    @GetMapping(value = {"/ingredient/list","/ingredient/list/{page}"})
    public String listIngredient(@PathVariable("page") Optional<Integer> page, Model model){
        try {
            Pageable pageable = PageRequest.of(page.orElse(1)-1, 10);
            Page<IngredientInfoDTO> ingredientList = ingredientService.list(pageable);
            model.addAttribute("ingredients", ingredientList);
            model.addAttribute("page", pageable.getPageNumber());
            model.addAttribute("maxPage", 5);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "목록 표시 중 에러가 발생하였습니다.");
            return "main";
        }
        return "ingredient/list";
    }

    @GetMapping(value = "/ingredient/create")
    public String createIngredient(Model model){
        model.addAttribute("ingredientFormDTO", new IngredientFormDTO());
        return "ingredient/create";
    }

    @PostMapping(value = "/ingredient/create")
    public String createIngredientPost(@Valid IngredientFormDTO ingredientFormDTO, BindingResult bindingResult, RedirectAttributes flash){
        if (bindingResult.hasErrors()){
            return "ingredient/create";
        }

        try {
            ingredientService.create(ingredientFormDTO);
        } catch (Exception e){
            flash.addFlashAttribute("errorMessage", "재료 수정 중 에러가 발생하였습니다.");
        }
        return "redirect:/ingredient/list";
    }

    @GetMapping(value = "/ingredient/update/{id}")
    public String updateIngredient(@PathVariable("id") Long id, Model model, RedirectAttributes flash){
        try {
            IngredientFormDTO ingredientFormDTO = ingredientService.original(id);
            model.addAttribute("ingredientFormDTO", ingredientFormDTO);
            return "ingredient/create";
        } catch (Exception e) {
            flash.addAttribute("errorMessage", "양식 표시 중 에러가 발생하였습니다.");
            return "redirect:/ingredient/list";
        }
    }

    @PostMapping(value = "/ingredient/update")
    public String updateIngredientPost(@Valid IngredientFormDTO ingredientFormDTO, BindingResult bindingResult, RedirectAttributes flash){
        if (bindingResult.hasErrors()){
            return "ingredient/create";
        }

        try {
            ingredientService.update(ingredientFormDTO);
        } catch (Exception e){
            flash.addFlashAttribute("errorMessage", "재료 수정 중 에러가 발생하였습니다.");
        }
        return "redirect:/ingredient/list";
    }

    @PostMapping(value = "/ingredient/delete/{id}")
    public @ResponseBody ResponseEntity<String> deleteIngredientPost(@PathVariable("id") Long id) {
        try {
            ingredientService.delete(id);
        } catch (Exception e) {
            return new ResponseEntity<>("재료 삭제 중 에러가 발생하였습니다.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(String.valueOf(id), HttpStatus.OK);
    }

    @PostMapping(value = "/ingredient/delete/{id}/full")
    public @ResponseBody ResponseEntity<String> deleteFullIngredientPost(@PathVariable("id") Long id) {
        try {
            ingredientService.deleteFull(id);
        } catch (Exception e) {
            return new ResponseEntity<>("재료 삭제 중 에러가 발생하였습니다.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(String.valueOf(id), HttpStatus.OK);
    }
}
