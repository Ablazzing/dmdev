package org.molodyko.integration;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.molodyko.dto.UserDto;
import org.molodyko.entity.User;
import org.molodyko.mapper.UserMapper;
import org.molodyko.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Transactional
class UserControllerTest extends IntegrationBase {
    private final MockMvc mockMvc;
    private final UserService userService;

    @Test
    @SneakyThrows
    void usersView() {
        int usersSize = userService.findAll().size();

        mockMvc.perform(get("/users"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attribute("users", hasSize(usersSize)));
    }

    @Test
    @SneakyThrows
    void createView() {
        UserDto userDto = UserDto.builder().build();
        mockMvc.perform(get("/users/create_user"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("userDto"))
                .andExpect(model().attribute("userDto", equalTo(userDto)));
    }

    @Test
    @SneakyThrows
    void updateView() {
        User user = userService.findById(1).orElseThrow();
        UserDto userDto = UserMapper.convertEntityToDto(user);

        mockMvc.perform(get("/users/update_user").param("id", "1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("userDto"))
                .andExpect(model().attribute("userDto", equalTo(userDto)));
    }
}