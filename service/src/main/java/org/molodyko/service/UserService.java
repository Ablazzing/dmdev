package org.molodyko.service;

import lombok.RequiredArgsConstructor;
import org.molodyko.dto.UserDto;
import org.molodyko.entity.User;
import org.molodyko.entity.filter.UserFilter;
import org.molodyko.mapper.UserMapper;
import org.molodyko.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public Optional<UserDto> findById(Integer id) {
        return userRepository.findById(id).map(userMapper::convertEntityToDto);
    }

    public UserDto update(UserDto userDto) {
        User user = userMapper.convertUserDtoToEntity(userDto);
        userRepository.saveAndFlush(user);
        return userMapper.convertEntityToDto(user);
    }

    public UserDto create(UserDto userDto) {
        User user = userMapper.convertUserDtoToEntity(userDto);
        userRepository.saveAndFlush(user);
        return userMapper.convertEntityToDto(user);
    }

    public boolean deleteById(Integer id) {
        return userRepository.findById(id)
                .map(entity -> {
                    userRepository.delete(entity);
                    return true;
                })
                .orElse(false);
    }

    public List<UserDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::convertEntityToDto)
                .collect(Collectors.toList());
    }

    public List<UserDto> findByFilter(UserFilter userFilter) {
        return userRepository.getUsersByFilter(userFilter)
                .stream()
                .map(userMapper::convertEntityToDto)
                .collect(Collectors.toList());
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
