package org.bookstore.bookstore.user.dto.response;

import java.time.LocalDateTime;

public record UserResponse(
        Long userId,
        String email,
        LocalDateTime createdAt) {
}
