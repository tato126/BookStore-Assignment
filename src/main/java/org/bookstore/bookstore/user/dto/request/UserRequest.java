package org.bookstore.bookstore.user.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UserRequest(
        @NotBlank String email,
        @NotBlank String password) {
}
