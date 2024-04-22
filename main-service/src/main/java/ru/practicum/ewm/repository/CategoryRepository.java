package ru.practicum.ewm.repository;

import org.springframework.data.repository.CrudRepository;
import ru.practicum.ewm.dto.category.CategoryDto;
import ru.practicum.ewm.entity.Category;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    boolean existsByName(String name);

    List<Category> findAll(Pageable pageable);
}
