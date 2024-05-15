package ru.practicum.ewm.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import ru.practicum.ewm.entity.Compilation;

import java.util.List;

public interface CompilationRepository extends CrudRepository<Compilation, Long> {
    List<Compilation> findAll(Pageable pageable);

    List<Compilation> findAllByIsPinned(boolean isPinned, Pageable pageable);
}
