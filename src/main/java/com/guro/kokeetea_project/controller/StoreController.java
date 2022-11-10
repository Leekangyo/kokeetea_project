package com.guro.kokeetea_project.controller;

import com.guro.kokeetea_project.dto.StoreFormDTO;
import com.guro.kokeetea_project.dto.StoreInfoDTO;
import com.guro.kokeetea_project.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;

    @GetMapping(value = {"/store/list","/store/list/{page}"})
    public String listStore(@PathVariable("page") Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.orElse(1)-1, 10);
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
    public String createStorePost(StoreFormDTO storeFormDTO){
        try {
            storeService.create(storeFormDTO);
        } catch (Exception e){

        }
        return "redirect:/store/list";
    }
}
