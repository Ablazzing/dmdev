package org.molodyko.controller;

import lombok.RequiredArgsConstructor;
import org.molodyko.dto.CategoryDto;
import org.molodyko.service.CategoryService;
import org.molodyko.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final UserService userService;

    @PostMapping
    public String createCategory(@ModelAttribute CategoryDto categoryDto, RedirectAttributes redirectAttributes) {
        if (!checkCategoryDto(categoryDto, redirectAttributes)) {
            return "redirect:categories/create";
        }

        categoryService.createCategory(categoryDto);
        redirectAttributes.addFlashAttribute("userNotFound", false);
        redirectAttributes.addFlashAttribute("success", true);

        return "redirect:categories/create";
    }

    @GetMapping("/create")
    public String createView(Model model) {
        fillDefaultAttributeModel(model);
        model.addAttribute("categoryDto", CategoryDto.builder().build());

        return "category/create-category";
    }

    @GetMapping
    public String getAllCategoriesView(Model model) {
        List<CategoryDto> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "category/categories";
    }

    @GetMapping("/{id}/update")
    public String getUpdateCategoryView(@PathVariable Integer id, Model model) {
        CategoryDto categoryDto = categoryService.findById(id);
        fillDefaultAttributeModel(model);
        model.addAttribute("categoryDto", categoryDto);
        return "category/update-category";
    }

    @PostMapping("/{id}/update")
    public String updateCategory(@PathVariable Integer id,
                                 @RequestBody CategoryDto categoryDto,
                                 RedirectAttributes redirectAttributes) {
        if (!checkCategoryDto(categoryDto, redirectAttributes)) {
            return "redirect:categories/create";
        }

        categoryDto.setId(id);
        categoryService.updateCategory(categoryDto);
        return "redirect:categories/create";
    }

    private void fillDefaultAttributeModel(Model model) {
        if (!model.containsAttribute("success")) {
            model.addAttribute("success", false);
        }
        if (!model.containsAttribute("userNotFound")) {
            model.addAttribute("userNotFound", false);
        }
        if (!model.containsAttribute("categoryExists")) {
            model.addAttribute("categoryExists", false);
        }
    }

    private boolean checkCategoryDto(CategoryDto categoryDto, RedirectAttributes redirectAttributes) {
        if (!userService.existsByUsername(categoryDto.getUsername())) {
            redirectAttributes.addFlashAttribute("userNotFound", true);
            return false;
        }
        if (!categoryService.existsByCategoryNameAndUserId(categoryDto.getName(), categoryDto.getUsername())) {
            redirectAttributes.addFlashAttribute("categoryExists", true);
            return false;
        }
        return true;
    }

}
