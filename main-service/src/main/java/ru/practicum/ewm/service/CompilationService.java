package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.compilation.CompilationDto;
import ru.practicum.ewm.dto.compilation.CompilationShortDto;
import ru.practicum.ewm.dto.compilation.CompilationUpdateDto;

import java.util.List;

public interface CompilationService {
    CompilationDto createCompilation(CompilationShortDto compilation);

    void deleteCompilationById(long id);

    CompilationDto updateCompilation(long id, CompilationUpdateDto compilation);

    List<CompilationDto> findAllCompilations(Boolean isPinned, int from, int size);

    CompilationDto findCompilationById(long compId);
}
