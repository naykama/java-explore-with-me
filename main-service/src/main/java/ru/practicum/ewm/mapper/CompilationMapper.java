package ru.practicum.ewm.mapper;

import ru.practicum.ewm.dto.compilation.CompilationDto;
import ru.practicum.ewm.dto.compilation.CompilationShortDto;
import ru.practicum.ewm.dto.event.EventCompilationDto;
import ru.practicum.ewm.entity.Compilation;
import ru.practicum.ewm.entity.Event;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class CompilationMapper {
    public static Compilation convertToEntity(CompilationShortDto dto, List<Event> events) {
        return Compilation.builder()
                .title(dto.getTitle())
                .isPinned(dto.isPinned())
                .events(new HashSet<>(events))
                .build();
    }

    public static Compilation convertToEntity(CompilationShortDto dto) {
        return Compilation.builder()
                .title(dto.getTitle())
                .isPinned(dto.isPinned())
                .build();
    }

    public static CompilationDto convertToDto(Compilation compilation, List<EventCompilationDto> events) {
        return new CompilationDto(compilation.getId(), events == null ? new ArrayList<>() : events,
                compilation.isPinned(), compilation.getTitle());
    }

    public static CompilationDto convertToDto(Compilation compilation) {
        return new CompilationDto(compilation.getId(), new ArrayList<>(),
                compilation.isPinned(), compilation.getTitle());
    }
}
