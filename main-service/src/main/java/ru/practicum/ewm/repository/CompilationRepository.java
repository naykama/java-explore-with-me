package ru.practicum.ewm.repository;

import org.springframework.data.repository.CrudRepository;
import ru.practicum.ewm.entity.Compilation;

public interface CompilationRepository extends CrudRepository<Compilation, Long> {
}
