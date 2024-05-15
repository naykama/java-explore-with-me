package ru.practicum.ewm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.category.CategoryDto;
import ru.practicum.ewm.dto.category.CategoryShortDto;
import ru.practicum.ewm.service.CategoryService;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
public class CategoryController {
    private static final String ADMIN_PATH = "/admin/categories";
    private static final String PUBLIC_PATH = "/categories";
    private final CategoryService categoryService;

    @PostMapping(ADMIN_PATH)
    @ResponseStatus(code = HttpStatus.CREATED)
    public CategoryDto createCategory(@Valid @RequestBody CategoryShortDto category) {
        log.info("Creating category name={}", category.getName());
        return categoryService.createCategory(category.getName());
    }

    @DeleteMapping(ADMIN_PATH + "/{catId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteCategoryById(@PathVariable Long catId) {
        log.info("Deleting category with id = {}", catId);
        categoryService.deleteCategoryById(catId);
    }

    @PatchMapping(ADMIN_PATH + "/{catId}")
    public CategoryDto updateCategory(@PathVariable Long catId,
                                      @RequestBody @NotEmpty Map<String, String> formParams) {
        String name = formParams.get("name");
        if (name == null || name.length() > 50) {
            throw new IllegalArgumentException("Name is not correct. Check: is not empty and length <= 50");
        }
        log.info("Updating category with id = {} name = {}", catId, name);
        return categoryService.updateCategory(catId, name);
    }

    @GetMapping(PUBLIC_PATH)
    public List<CategoryDto> findAll(@RequestParam(defaultValue = "0") int from,
                                     @RequestParam(defaultValue = "10") int size) {
        log.info("Finding categories");
        return categoryService.findAll(from, size);
    }

    @GetMapping(PUBLIC_PATH + "/{catId}")
    public CategoryDto findById(@PathVariable long catId) {
        log.info("Finding category with id = {}", catId);
        return categoryService.findById(catId);
    }
}
