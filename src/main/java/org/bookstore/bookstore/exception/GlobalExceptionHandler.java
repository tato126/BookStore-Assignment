package org.bookstore.bookstore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public String handleResponseStatusException(ResponseStatusException ex, Model model) {
        if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
            model.addAttribute("message", ex.getReason());
            return "errors/404"; // 404 페이지
        }
        return "errors/error"; // 기타 오류용 기본 페이지
    }
}