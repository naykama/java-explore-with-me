package ru.practicum.ewm.server.stats;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.stats.EventDto;
import ru.practicum.ewm.dto.stats.EventGetDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StatController {
    private final StatService service;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveEvent(@RequestBody EventDto eventDto) {
        log.info("Saving event: ip = {}, date = {}", eventDto.getIp(), eventDto.getExistDate());
        service.saveEvent(eventDto);
    }

    @GetMapping("/stats")
    public List<EventGetDto> findEvents(@RequestParam String start, @RequestParam String end,
                                        @RequestParam(required = false) String[] uris,
                                        @RequestParam(defaultValue = "false") boolean unique) {
        log.info("Finding events: start = {}, end = {}", start, end);
        return service.findEvents(start, end, uris, unique);
    }
}
