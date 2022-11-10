package com.guro.kokeetea_project.controller;

import com.guro.kokeetea_project.dto.StoreFormDTO;
import com.guro.kokeetea_project.dto.StoreInfoDTO;
import com.guro.kokeetea_project.service.StoreService;
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

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;

    @GetMapping(value = {"/store/list","/store/list/{page}"})
    public String listStore(@PathVariable("page") Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.orElse(0), 10);
        Page<StoreInfoDTO> storeList = storeService.list(pageable);

        model.addAttribute("stores", storeList);
        model.addAttribute("maxPage", 5);

        return "store/list";
    }

    @GetMapping(value = "/store/create")
    public String createStore(Model model){
        model.addAttribute("storeFormDTO", new StoreFormDTO());
        return "store/create";
    }

    @PostMapping(value = "/store/create")
    public String createStorePost(@Valid StoreFormDTO storeFormDTO, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            return "/store/create";
        }

        try {
            storeService.create(storeFormDTO);
        } catch (Exception e){
            model.addAttribute("errorMessage", "재료 등록 중 에러가 발생하였습니다.");
            return "redirect:/store/create";
        }
        return "redirect:/store/list";
    }

    @PostMapping(value = "/store/delete/{id}")
    public ResponseEntity<String> deleteStorePost(@PathVariable("id") Long id) {
        try {
            storeService.delete(id);
        } catch (Exception e) {
            return new ResponseEntity<>("가맹점 삭제 중 에러가 발생하였습니다.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(String.valueOf(id), HttpStatus.OK);
    }

    @PostMapping(value = "/store/delete/{id}/full")
    public ResponseEntity<String> deleteFullStorePost(@PathVariable("id") Long id) {
        try {
            storeService.deleteFull(id);
        } catch (Exception e) {
            return new ResponseEntity<>("가맹점 삭제 중 에러가 발생하였습니다.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(String.valueOf(id), HttpStatus.OK);
    }
}
