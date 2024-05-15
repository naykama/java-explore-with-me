package ru.practicum.ewm.dto.user;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
public class UserShortDto extends UserBaseDto {
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email is not correct")
    @Size(min = 6, max = 254)
    private final String email;

    public UserShortDto(String email, String name) {
        super(name);
        this.email = email;
    }
}
