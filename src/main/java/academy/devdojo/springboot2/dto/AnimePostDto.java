package academy.devdojo.springboot2.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AnimePostDto {

    @NotEmpty
    private String name;
}
