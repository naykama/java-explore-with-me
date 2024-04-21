package ru.practicum.ewm.dto.category;

import lombok.Getter;

@Getter
public class CategoryDto extends CategoryShortDto {
    private final Long id;

    public CategoryDto(Long id, String name) {
        super(name);
        this.id = id;
    }
}
