package com.api.usuario.config.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Getter
@Setter
public class ExceptionResponse extends RuntimeException {

  public ExceptionResponse(String message) {
    super(message);
  }
}
