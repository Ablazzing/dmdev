package org.molodyko.mapper;

import org.molodyko.dto.UserDto;
import org.molodyko.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public User convertUserDtoToEntity(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .email(userDto.getEmail())
                .role(userDto.getRole())
                .build();
    }

    public UserDto convertEntityToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }
}
