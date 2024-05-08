package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.compilation.CompilationDto;
import ru.practicum.ewm.dto.compilation.CompilationShortDto;

public interface CompilationService {
    CompilationDto createCompilation(CompilationShortDto compilation);
}
