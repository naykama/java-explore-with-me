package ru.practicum.ewm.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import ru.practicum.ewm.entity.Category;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    boolean existsByName(String name);

    List<Category> findAll(Pageable pageable);
}
