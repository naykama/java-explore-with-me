package ru.practicum.ewm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.compilation.CompilationDto;
import ru.practicum.ewm.dto.compilation.CompilationShortDto;
import ru.practicum.ewm.dto.compilation.CompilationUpdateDto;
import ru.practicum.ewm.service.CompilationService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
public class CompilationController {
    private final CompilationService compilationService;

    private static final String ADMIN_PATH = "/admin/compilations";
    private static final String PUBLIC_PATH = "/compilations";

    @PostMapping(ADMIN_PATH)
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto createCompilation(@Valid @RequestBody CompilationShortDto compilation) {
        log.info("Creating compilation with title: {}", compilation.getTitle());
        return compilationService.createCompilation(compilation);
    }

    @DeleteMapping(ADMIN_PATH + "/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilationById(@PathVariable long compId) {
        log.info("Deleting compilation with id = {}", compId);
        compilationService.deleteCompilationById(compId);
    }

    @PatchMapping(ADMIN_PATH + "/{compId}")
    public CompilationDto updateCompilation(@PathVariable long compId, @Valid @RequestBody CompilationUpdateDto compilation) {
        log.info("Updating compilation with id = {}", compId);
        return compilationService.updateCompilation(compId, compilation);
    }
}
