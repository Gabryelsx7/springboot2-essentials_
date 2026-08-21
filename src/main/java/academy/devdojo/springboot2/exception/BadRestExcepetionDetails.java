package academy.devdojo.springboot2.exception;

import lombok.Builder;
import lombok.Data;
import org.hibernate.boot.jaxb.internal.stax.LocalSchemaLocator;

import java.time.LocalDateTime;

@Data
@Builder
public class BadRestExcepetionDetails {

    private String title;
    private int status;
    private String details;
    private String developerMessage;
    private LocalDateTime timestamp;


}
