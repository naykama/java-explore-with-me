package ru.practicum.ewm.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.request.RequestDto;
import ru.practicum.ewm.entity.Event;
import ru.practicum.ewm.entity.Request;
import ru.practicum.ewm.entity.User;
import ru.practicum.ewm.entity.enums.RequestType;
import ru.practicum.ewm.entity.enums.StateType;
import ru.practicum.ewm.entity.exception.ConflictException;
import ru.practicum.ewm.entity.exception.NotFoundException;
import ru.practicum.ewm.mapper.RequestMapper;
import ru.practicum.ewm.repository.EventRepository;
import ru.practicum.ewm.repository.RequestRepository;
import ru.practicum.ewm.repository.UserRepository;
import ru.practicum.ewm.service.RequestService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.ewm.mapper.RequestMapper.convertToDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public RequestDto createRequest(long userId, long eventId, LocalDateTime createDate) {
        User requester = userRepository.findById(userId).orElseThrow(() -> {
            log.error("User with id = {} not found", userId);
            return new NotFoundException(String.format("User with id = %d not found", userId));
        });
        Event event = eventRepository.findById(eventId).orElseThrow(() -> {
            log.error("Event with id = {} not found", eventId);
            throw new NotFoundException(String.format("Event with id = %d not found", eventId));
        });
        checkCreateRequest(requester, event);
        return convertToDto(requestRepository.save(Request.builder().requester(requester).event(event)
                .createDate(createDate).status(!event.isRequestModeration() || event.getParticipantLimit() == 0 ?
                        RequestType.CONFIRMED : RequestType.PENDING).build()));
    }

    @Override
    public List<RequestDto> findAll(long userId) {
        return requestRepository.findAllByRequesterId(userId).stream()
                .map(RequestMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public RequestDto cancelRequest(long userId, long requestId) {
        Request request = requestRepository.findById(requestId).orElseThrow(() -> {
            log.error("Request with id = {} not found", requestId);
            return new NotFoundException(String.format("Request with id = %d not found", requestId));
        });
        if (request.getRequester().getId() != userId) {
            log.error("User with id = {} is not a requester", userId);
            throw  new ConflictException(String.format("User with id = %d not a requester", userId));
        }
        request.setStatus(RequestType.CANCELED);
        return convertToDto(requestRepository.save(request));
    }

    private void checkCreateRequest(User requester, Event event) {
        if (requester.equals(event.getInitiator())) {
            log.error("Initiator cannot send request for his event");
            throw new ConflictException("Initiator cannot send request for his event");
        }
        if (requestRepository.existsByEventIdAndRequesterId(event.getId(), requester.getId())) {
            log.error("This request is already passed");
            throw new ConflictException("This request is already passed");
        }
        if (event.getState() != StateType.PUBLISHED) {
            log.error("You can take part only in published events");
            throw new ConflictException("You can take part only in published events");
        }
        if (event.getParticipantLimit() != 0 && requestRepository.countByEventIdAndStatus(event.getId(), RequestType.CONFIRMED) >= event.getParticipantLimit()) {
            log.error("Limit of participants will overload");
            throw new ConflictException("Limit of participants will overload");
        }
    }
}
