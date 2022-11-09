package com.guro.kokeetea_project.controller;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.guro.kokeetea_project.dto.RequestFormDTO;
import com.guro.kokeetea_project.dto.RequestInfoDTO;
import com.guro.kokeetea_project.service.RequestService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class RequestController {
    private final RequestService requestService;

    @GetMapping(value = {"/request/list","/request/list/{page}"})
    public String listRequest(@PathVariable("page") Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.orElse(0), 10);
        Page<RequestInfoDTO> requestList = requestService.list(pageable);

        model.addAttribute("requests", requestList);
        model.addAttribute("maxPage", 5);

        return "request/list";
    }

    @GetMapping(value = "/request/create")
    public String createRequest(Model model){
        model.addAttribute("requestFormDTO", new RequestFormDTO());
        model.addAttribute("ingredients", requestService.ingredients());
        model.addAttribute("stores", requestService.stores());
        return "request/create";
    }

    @PostMapping(value = "/request/create")
    public String createRequestPost(RequestFormDTO requestFormDTO){
        try {
            requestService.create(requestFormDTO);
        } catch (Exception e){

        }
        return "redirect:/request/list";
    }
}
