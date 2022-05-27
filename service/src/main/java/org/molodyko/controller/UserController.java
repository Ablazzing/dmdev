package org.molodyko.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.molodyko.dto.UserDto;
import org.molodyko.entity.User;
import org.molodyko.entity.UserRole;
import org.molodyko.entity.filter.UserFilter;
import org.molodyko.mapper.UserMapper;
import org.molodyko.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("users")
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping
    public String usersView(Model model,
                            @RequestParam(required = false) String username,
                            @RequestParam(required = false) String role) {
        UserFilter userFilter = UserFilter.builder()
                .username(username != null && !username.isEmpty() ? username : null)
                .role(role != null && !role.isEmpty() ? UserRole.valueOf(role) : null)
                .build();
        model.addAttribute("users", userService.findByFilter(userFilter));
        return "users";
    }

    @GetMapping("create_user")
    public String createView(Model model) {
        model.addAttribute("userDto", UserDto.builder().build());
        return "create_user";
    }

    @GetMapping("update_user")
    public String updateView(Model model, @RequestParam Integer id) {
        try {
            User user = userService.findById(id).orElseThrow();
            UserDto userDto = UserMapper.convertEntityToDto(user);
            model.addAttribute("userDto", userDto);
            return "create_user";
        } catch (Exception e) {
            return "redirect:users";
        }

    }

    @PostMapping
    public String save(@ModelAttribute UserDto userDto) {
        if (userDto.getId() != null) {
            userService.update(userDto);
        } else {
            userService.create(userDto);
        }
        return "redirect:/users/actions";
    }

    @PostMapping("delete/{id}")
    public String delete(@PathVariable Integer id) {
        userService.deleteById(id);
        return "redirect:/users";
    }
}
