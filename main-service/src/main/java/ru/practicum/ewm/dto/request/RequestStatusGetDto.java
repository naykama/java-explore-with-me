package ru.practicum.ewm.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class RequestStatusGetDto {
    List<RequestDto> confirmedRequests = new ArrayList<>();
    List<RequestDto> rejectedRequests = new ArrayList<>();
}
