package ru.practicum.ewm.dto.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.ewm.entity.enums.AdminActionType;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class EventAdminUpdateDto extends EventBaseUpdateDto {
    private AdminActionType stateAction;
}
