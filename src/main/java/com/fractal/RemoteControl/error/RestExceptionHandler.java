package com.fractal.RemoteControl.error;

import java.time.LocalDateTime;

import com.fractal.RemoteControl.enums.Error;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    
    @ResponseBody
    @ExceptionHandler(CommandNotExecutedException.class)
    public ResponseEntity<?> handleCommandNotExecutedException(CommandNotExecutedException ex) {
        MasterErrorDetails masterErrorDetails = MasterErrorDetails.Builder
                .newBuilder()
                .title(Error.COMMAND_COULD_NOT_BE_EXECUTED.getTitle())
                .timestamp(LocalDateTime.now())
                .detail(ex.getMessage())
                .developerMessage("")
                .build();

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(masterErrorDetails);
    }

    @ResponseBody
    @ExceptionHandler(InternalServerError.class)
	protected ResponseEntity<?> handleGlobalException(InternalServerError ex) {
		MasterErrorDetails masterErrorDetails = MasterErrorDetails.Builder
                .newBuilder()
                .title(Error.INTERNAL_SERVER_ERROR.getTitle())
                .timestamp(LocalDateTime.now())
                .detail(Error.INTERNAL_SERVER_ERROR.getMessage())
                .developerMessage("")
                .build();
        
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(masterErrorDetails);
	} 

	@ResponseBody
    @Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
		MasterErrorDetails masterErrorDetails = MasterErrorDetails.Builder
                .newBuilder()
                .title(Error.INTERNAL_SERVER_ERROR.getTitle())
                .timestamp(LocalDateTime.now())
                .detail(Error.INTERNAL_SERVER_ERROR.getMessage())
                .developerMessage("")
                .build();
                
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(masterErrorDetails);
    }

}
