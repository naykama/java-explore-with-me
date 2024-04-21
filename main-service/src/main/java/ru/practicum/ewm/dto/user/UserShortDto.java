package ru.practicum.ewm.dto.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@RequiredArgsConstructor
@Getter
public class UserShortDto {
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email is not correct")
    @Size(min = 6, max = 254)
    private final String email;
    @NotBlank(message = "Name cannot be empty")
    @Size(min = 2, max = 250)
    private final String name;
}
