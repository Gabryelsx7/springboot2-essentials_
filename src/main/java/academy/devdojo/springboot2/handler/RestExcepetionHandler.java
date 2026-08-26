package academy.devdojo.springboot2.handler;

import academy.devdojo.springboot2.exception.BadRequestException;
import academy.devdojo.springboot2.exception.BadRestExcepetionDetails;
import academy.devdojo.springboot2.exception.ExcepetionDetails;
import academy.devdojo.springboot2.exception.ValidationExcepetionDetails;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExcepetionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRestExcepetionDetails> handleBadRequestException(BadRequestException bre) {
        return new ResponseEntity<>(
                BadRestExcepetionDetails.builder()
                        .timestamp(LocalDateTime.now()).
                        title("Bad Request Exception, Check the Document").
                        status(HttpStatus.BAD_REQUEST.value()).
                        details(bre.getMessage()).
                        developerMessage(bre.getClass().getName()).
                        build(), HttpStatus.BAD_REQUEST);
    }
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        List<FieldError> fieldErrors =
                exception.getBindingResult().getFieldErrors();

        String fields = fieldErrors.stream()
                .map(FieldError::getField)
                .collect(Collectors.joining(", "));

        String fieldsMessage = fieldErrors.stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        return new ResponseEntity<>(
                ValidationExcepetionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .title("Bad Request Exception, Invalid fields")
                        .status(HttpStatus.BAD_REQUEST.value())
                        .details(exception.getMessage())
                        .developerMessage(exception.getClass().getName())
                        .fields(fields)
                        .fieldsMessage(fieldsMessage)
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

    protected ResponseEntity<Object> handlerExcepetionInternal(
           Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ExcepetionDetails excepetionDetails = ExcepetionDetails.builder()
                .timestamp(LocalDateTime.now()).
                title(ex.getCause().getMessage()).
                status(status.value()).
                details(ex.getMessage()).
                developerMessage(ex.getClass().getName()).
                build();


        return new ResponseEntity<>(excepetionDetails, headers, status);
    }
}
