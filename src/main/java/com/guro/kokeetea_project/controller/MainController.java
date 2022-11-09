package com.guro.kokeetea_project.controller;

import com.guro.kokeetea_project.dto.MainItemDto;
import com.guro.kokeetea_project.service.OLD_ItemService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final OLD_ItemService OLDItemService;

    @GetMapping(value="/")
    public String main(Optional<Integer> page, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.isAssignableFrom(authentication.getClass())) {
            return "redirect:/member/login";
        }

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0,6);
        Page<MainItemDto> items = OLDItemService.getMainItemPage(pageable);
        model.addAttribute("items",items);
        model.addAttribute("maxPage",5);

        return "main";
    }



}