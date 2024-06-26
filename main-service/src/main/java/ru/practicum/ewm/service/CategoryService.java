package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.category.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(String name);

    void deleteCategoryById(Long id);

    CategoryDto updateCategory(Long id, String name);

    List<CategoryDto> findAll(int from, int size);

    CategoryDto findById(long id);
}
