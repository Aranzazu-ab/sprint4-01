package cat.itacademy.s04.t01.userapi.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRequest(
    @NotBlank(message = "name is required")
    String name,

    @NotBlank (message = "name is required")
    @Email (message = "email must be valid")
    String email
){
}
