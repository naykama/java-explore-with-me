package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.category.CategoryDto;

public interface CategoryService {
    CategoryDto createCategory(String name);

    void deleteCategoryById(Long id);

    CategoryDto updateCategory(Long id, String name);
}
