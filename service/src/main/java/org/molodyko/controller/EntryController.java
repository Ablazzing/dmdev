package org.molodyko.controller;

import lombok.RequiredArgsConstructor;
import org.molodyko.dto.EntryDto;
import org.molodyko.service.CategoryService;
import org.molodyko.service.EntryService;
import org.molodyko.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/entries")
public class EntryController {

    private final EntryService entryService;
    private final UserService userService;
    private final CategoryService categoryService;

    @GetMapping("/create")
    public String createEntryView(Model model) {
        fillDefaultAttributeModel(model);
        model.addAttribute("entryDtoRq", EntryDto.builder().build());
        return "entry/create-entry";
    }

    @PostMapping
    public String createEntry(@ModelAttribute EntryDto entryDto, RedirectAttributes redirectAttributes) {
        entryService.save(entryDto);
        redirectAttributes.addFlashAttribute("success", true);
        return "redirect:entries/create";
    }

    @GetMapping
    public String getEntriesView(Model model) {
        model.addAttribute("entries", entryService.getEntries());
        return "entry/entries";
    }

    @GetMapping("/{id}/update")
    public String updateEntriesView(@PathVariable Integer id, Model model) {
        fillDefaultAttributeModel(model);
        EntryDto entryDto = entryService.getEntryById(id);
        model.addAttribute("entryDtoRq", entryDto);
        return "entry/update-entry";
    }

    @PostMapping("/{id}/update")
    public String updateEntries(@ModelAttribute EntryDto entryDto,
                                RedirectAttributes redirectAttributes,
                                @PathVariable Integer id) {
        checkEntryDto(entryDto, redirectAttributes);
        entryService.save(entryDto);
        redirectAttributes.addFlashAttribute("success", true);
        return "redirect:/entries/" + id + "/update";
    }

    @PostMapping("/{id}/delete")
    public String deleteMapping(@PathVariable Integer id) {
        entryService.deleteEntryById(id);
        return "redirect:/entries";
    }

    private void fillDefaultAttributeModel(Model model) {
        if (!model.containsAttribute("success")) {
            model.addAttribute("success", false);
        }
        if (!model.containsAttribute("userNotFound")) {
            model.addAttribute("userNotFound", false);
        }
        if (!model.containsAttribute("categoryNotFound")) {
            model.addAttribute("categoryNotFound", false);
        }
    }

    private boolean checkEntryDto(EntryDto entryDto, RedirectAttributes redirectAttributes) {
        if (!userService.existsByUsername(entryDto.getUsername())) {
            redirectAttributes.addFlashAttribute("userNotFound", true);
            return false;
        }
        if (!categoryService.existsByCategoryNameAndUserId(entryDto.getCategoryName(), entryDto.getUsername())) {
            redirectAttributes.addFlashAttribute("categoryNotFound", true);
            return false;
        }
        return true;
    }
}
