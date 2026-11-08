package cat.itacademy.s04.t01.userapi.model;

import java.util.UUID;

public record UserResponse(UUID id, String name, String email) {
}
