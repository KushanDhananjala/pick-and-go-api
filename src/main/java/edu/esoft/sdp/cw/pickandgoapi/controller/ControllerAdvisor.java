package edu.esoft.sdp.cw.pickandgoapi.controller;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import edu.esoft.sdp.cw.pickandgoapi.exception.NotFoundException;
import edu.esoft.sdp.cw.pickandgoapi.exception.PickAndGoBadRequest;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

  public static final String TIMESTAMP = "timestamp";
  public static final String MESSAGE = "message";

  @ExceptionHandler(NotFoundException.class)
  protected ResponseEntity<Object> handleNotFoundError(
      final RuntimeException ex, final WebRequest request) {

    final Map<String, Object> body = new LinkedHashMap<>();
    body.put(TIMESTAMP, LocalDateTime.now());
    body.put(MESSAGE, ex.getMessage());

    return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(UsernameNotFoundException.class)
  protected ResponseEntity<Object> handleUserNameNotFoundError(
      final UsernameNotFoundException ex, final WebRequest request) {

    final Map<String, Object> body = new LinkedHashMap<>();
    body.put(TIMESTAMP, LocalDateTime.now());
    body.put(MESSAGE, ex.getMessage());

    return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(Exception.class)
  protected ResponseEntity<Object> handleInternalServerError(
      final RuntimeException ex, final WebRequest request) {

    final Map<String, Object> body = new LinkedHashMap<>();
    body.put(TIMESTAMP, LocalDateTime.now());
    body.put(MESSAGE, ex.getMessage());

    return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(PickAndGoBadRequest.class)
  protected ResponseEntity<Object> handleBadRequest(final PickAndGoBadRequest ex) {

    final Map<String, Object> body = new LinkedHashMap<>();
    body.put(TIMESTAMP, LocalDateTime.now());
    body.put(MESSAGE, ex.getMessage());

    return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
  }
}
