package ru.practicum.ewm.mapper;

import ru.practicum.ewm.dto.category.CategoryDto;
import ru.practicum.ewm.entity.Category;

public class CategoryMapper {
    public static Category convertToEntity(String name) {
        return new Category(name);
    }

    public static CategoryDto convertToDto(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }
}
