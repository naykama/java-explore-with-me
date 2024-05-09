package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.compilation.CompilationDto;
import ru.practicum.ewm.dto.compilation.CompilationShortDto;
import ru.practicum.ewm.dto.compilation.CompilationUpdateDto;

public interface CompilationService {
    CompilationDto createCompilation(CompilationShortDto compilation);

    void deleteCompilationById(long id);

    CompilationDto updateCompilation(long id, CompilationUpdateDto compilation);
}
