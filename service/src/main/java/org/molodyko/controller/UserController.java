package org.molodyko.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.molodyko.dto.UserDto;
import org.molodyko.entity.UserRole;
import org.molodyko.entity.filter.UserFilter;
import org.molodyko.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.notFound;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final UserService userService;

    @ExceptionHandler(Exception.class)
    public String handleExceptions(Exception exception, HttpServletRequest request) {
        throw new ResponseStatusException(NOT_FOUND, "Unable to find resource");
    }

    @GetMapping
    public List<UserDto> users(
                            @RequestParam(required = false) String username,
                            @RequestParam(required = false) String role) {
        UserFilter userFilter = UserFilter.builder()
                .username(username != null && !username.isEmpty() ? username : null)
                .role(role != null && !role.isEmpty() ? UserRole.valueOf(role) : null)
                .build();
        return userService.findByFilter(userFilter);
    }


    @PostMapping
    public UserDto save(@RequestBody UserDto userDto) {
        return userService.create(userDto);
    }

    @PostMapping("/{id}/update")
    public UserDto update(@PathVariable Integer id, @RequestBody UserDto userDto) {
        userDto.setId(id);
        return userService.update(userDto);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        return userService.deleteById(id)
                ? noContent().build()
                : notFound().build();
    }
}
