package org.molodyko.mapper;

import lombok.RequiredArgsConstructor;
import org.molodyko.dto.CategoryDto;
import org.molodyko.entity.Category;
import org.molodyko.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryMapper {

    private final UserRepository userRepository;

    public Category convertDtoToEntity(CategoryDto categoryDto) {
        return Category.builder()
                .id(categoryDto.getId())
                .name(categoryDto.getName())
                .user(
                        userRepository.findUserByUsername(categoryDto.getUsername())
                                .orElseThrow()
                )
                .build();
    }

    public CategoryDto convertEntityToDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .username(category.getUser().getUsername())
                .build();
    }
}
