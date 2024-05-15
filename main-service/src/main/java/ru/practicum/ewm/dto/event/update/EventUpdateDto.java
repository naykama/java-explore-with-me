package ru.practicum.ewm.dto.event.update;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.ewm.entity.enums.UserActionType;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class EventUpdateDto extends EventBaseUpdateDto {
    private UserActionType stateAction;
}
