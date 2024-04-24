package ru.practicum.ewm.dto.category;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CategoryDto extends CategoryShortDto {
    private Long id;

    public CategoryDto(Long id, String name) {
        super(name);
        this.id = id;
    }

    public CategoryDto(Long id) {
        this.id = id;
    }
}
