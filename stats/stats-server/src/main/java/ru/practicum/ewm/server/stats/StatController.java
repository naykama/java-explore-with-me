package ru.practicum.ewm.server.stats;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.stats.StatEventDto;
import ru.practicum.ewm.dto.stats.StatEventGetDto;
import ru.practicum.ewm.server.stats.service.StatService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
public class StatController {
    private final StatService service;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public Long saveEvent(@RequestBody @Valid StatEventDto eventDto) {
        log.info("Saving event: ip = {}, date = {}, uri = {}", eventDto.getIp(), eventDto.getExistDate(), eventDto.getUri());
        return service.saveEvent(eventDto);
    }

    @GetMapping("/stats")
    public List<StatEventGetDto> findEvents(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
                                            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
                                            @RequestParam(required = false) String[] uris,
                                            @RequestParam(defaultValue = "false") boolean unique) {
        if (start.isAfter(end)) {
            log.error("Start date must be before end date");
            throw new IllegalArgumentException("Start date must be before end date");
        }
        log.info("Finding events: start = {}, end = {}", start, end);
        return service.findEvents(start, end, uris, unique);
    }
}
