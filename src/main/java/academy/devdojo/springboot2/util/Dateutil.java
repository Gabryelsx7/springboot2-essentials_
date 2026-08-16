package academy.devdojo.springboot2.util;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class Dateutil {
    public String formatLocalDateTimeToDataBaseStryle(LocalDateTime localDateTime){
        return DateTimeFormatter.ofPattern("yyyy-MM-dd- HH:mm:ss").format(localDateTime);
    }
}
