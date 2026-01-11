package com.restclientgithub;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice(assignableTypes = GithubController.class)

public class UserErrorHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorUserResponseDto> handleNotFoundException(UserNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorUserResponseDto(
                        HttpStatus.NOT_FOUND.value(),
                        exception.getMessage()
                ));


    }
}
