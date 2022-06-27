package org.molodyko.controller;

import org.molodyko.dto.UserDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserViewController {

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    @GetMapping("/registration")
    public String createUser(Model model) {
        model.addAttribute("userDto", UserDto.builder().build());
        return "user/create-user";
    }
}
