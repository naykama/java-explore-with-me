package ru.practicum.ewm.repository;

import org.springframework.data.repository.CrudRepository;
import ru.practicum.ewm.entity.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    boolean existsByName(String name);
}
