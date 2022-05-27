package org.molodyko.service;

import lombok.RequiredArgsConstructor;
import org.molodyko.dto.UserDto;
import org.molodyko.entity.User;
import org.molodyko.entity.filter.UserFilter;
import org.molodyko.mapper.UserMapper;
import org.molodyko.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    public void update(UserDto userDto) {
        User user = UserMapper.convertUserDtoToEntity(userDto);
        userRepository.saveAndFlush(user);
    }

    public void create(UserDto userDto) {
        User user = UserMapper.convertUserDtoToEntity(userDto);
        userRepository.saveAndFlush(user);
    }

    public void deleteById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User with %d is not found for delete".formatted(id)));
        userRepository.delete(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<User> findByFilter(UserFilter userFilter) {
        return userRepository.getUsersByFilter(userFilter);
    }
}
