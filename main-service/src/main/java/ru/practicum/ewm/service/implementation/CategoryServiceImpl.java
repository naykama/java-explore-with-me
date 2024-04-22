package ru.practicum.ewm.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.category.CategoryDto;
import ru.practicum.ewm.entity.Category;
import ru.practicum.ewm.entity.exception.AlreadyExistException;
import ru.practicum.ewm.entity.exception.NotFoundException;
import ru.practicum.ewm.repository.CategoryRepository;
import ru.practicum.ewm.service.CategoryService;

import static ru.practicum.ewm.mapper.CategoryMapper.convertToDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDto createCategory(String name) {
        try {
            return convertToDto(categoryRepository.save(new Category(name)));
        } catch (DataIntegrityViolationException e) {
            log.error("Category with name = {} is already exists", name);
            throw new AlreadyExistException(String.format("Category with name = %s is already exists", name));
        }
    }

    @Override
    public void deleteCategoryById(Long id) {
        // Проверить, есть ли события в категории (когда будет создан Event)
        try {
            categoryRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            log.error("Category with id = {} not found", id);
            throw new NotFoundException(String.format("Category with id = %d not found", id));
        }
    }

    @Override
    public CategoryDto updateCategory(Long id, String name) {
        if (!categoryRepository.existsById(id)) {
            log.error("Category with id = {} not found", id);
            throw new NotFoundException(String.format("Category with id = %d not found", id));
        }
        try {
            return convertToDto(categoryRepository.save(new Category(id, name)));
        } catch (DataIntegrityViolationException e) {
            log.error("Category with name = {} is already exists", name);
            throw new AlreadyExistException(String.format("Category with name = %s is already exists", name));
        }
    }
}
