package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.category.CategoryDto;

public interface CategoryService {
    CategoryDto createCategory(String name);
}
