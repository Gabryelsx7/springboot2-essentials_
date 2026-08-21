package academy.devdojo.springboot2.handler;

import academy.devdojo.springboot2.exception.BadRequestException;
import academy.devdojo.springboot2.exception.BadRestExcepetionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestExcepetionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRestExcepetionDetails> handlerBadRequestException(BadRequestException bre){
        return new ResponseEntity<>(
                BadRestExcepetionDetails.builder()
                        .timestamp(LocalDateTime.now()).
                        title("Bad Request Exception, Check the Document").
                        status(HttpStatus.BAD_REQUEST.value()).
                        details(bre.getMessage()).
                        developerMessage(bre.getClass().getName()).
                        build(), HttpStatus.BAD_REQUEST);
    }
}
