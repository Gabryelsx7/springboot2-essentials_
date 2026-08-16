package academy.devdojo.springboot2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

//@ComponentScan
//@EnableAutoConfiguration
//@Configuration
@SpringBootApplication
public class AplicationStart {
    public static void main(String[] args) {
        SpringApplication.run(AplicationStart.class, args);
    }
}
