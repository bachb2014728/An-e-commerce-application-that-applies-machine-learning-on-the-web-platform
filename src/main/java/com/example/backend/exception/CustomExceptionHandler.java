package com.example.backend.exception;

import com.example.backend.exception.error.AlreadyExistException;
import com.example.backend.exception.error.NotFoundException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler({NotFoundException.class})
    public ProblemDetail handleNotFoundException(Exception ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(404), ex.getMessage());
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setProperty("timestamp", ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")));
        return problemDetail;
    }
    @ExceptionHandler({BadCredentialsException.class})
    public ProblemDetail handleBadCredentialsException(Exception ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(404), ex.getMessage());
        problemDetail.setDetail("Email hoặc mật khẩu không đúng!");
        problemDetail.setProperty("timestamp", ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")));
        return problemDetail;
    }
    @ExceptionHandler({AlreadyExistException.class})
    public ProblemDetail handleAlreadyExistException(RuntimeException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(409),ex.getMessage());
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setProperty("timestamp", ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")));
        return problemDetail;
    }
}
