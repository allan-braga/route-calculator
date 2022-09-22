package com.pwc.routescalculator.api;

import static org.springframework.http.ResponseEntity.badRequest;

import com.pwc.routescalculator.exception.PathNotFound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiControllerAdvice {

  @ExceptionHandler(PathNotFound.class)
  ResponseEntity postNotFound(PathNotFound ex) {
    log.debug("handling exception::" + ex);
    return badRequest().build();
  }
}
