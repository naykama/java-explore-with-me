package ru.practicum.ewm.server.stats;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.dto.stats.EventDto;

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


}
