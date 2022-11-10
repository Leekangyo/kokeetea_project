package com.guro.kokeetea_project.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MainController {

    @GetMapping(value="/")
    public String main(Optional<Integer> page, Model model, Principal principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.isAssignableFrom(authentication.getClass())) {
            return "redirect:/member/login";
        }

        model.addAttribute("username", principal.getName());

        return "main";
    }
}