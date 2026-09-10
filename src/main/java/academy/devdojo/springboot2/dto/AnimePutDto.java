package academy.devdojo.springboot2.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AnimePutDto {
    private Long id;
    private String name;
}
