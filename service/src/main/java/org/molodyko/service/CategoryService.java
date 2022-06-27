package org.molodyko.service;

import lombok.RequiredArgsConstructor;
import org.molodyko.dto.CategoryDto;
import org.molodyko.entity.Category;
import org.molodyko.mapper.CategoryMapper;
import org.molodyko.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = categoryMapper.convertDtoToEntity(categoryDto);
        categoryRepository.saveAndFlush(category);
        return categoryMapper.convertEntityToDto(category);
    }

    public CategoryDto updateCategory(CategoryDto categoryDto) {
        Category category = categoryMapper.convertDtoToEntity(categoryDto);
        categoryRepository.saveAndFlush(category);
        return categoryMapper.convertEntityToDto(category);
    }

    public CategoryDto findById(Integer id) {
        Category category = categoryRepository.findById(id).orElseThrow();
        return categoryMapper.convertEntityToDto(category);
    }

    public List<CategoryDto> findAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(category -> categoryMapper.convertEntityToDto(category))
                .collect(Collectors.toList());
    }

    public boolean deleteById(Integer id) {
        return categoryRepository.findById(id)
                .map(category -> {
                    categoryRepository.deleteById(id);
                    return true;
                })
                .orElse(false);
    }

    public boolean existsByCategoryNameAndUserId(String name, String username) {
        return categoryRepository.existsByNameAndUser_Username(name, username);
    }
}
