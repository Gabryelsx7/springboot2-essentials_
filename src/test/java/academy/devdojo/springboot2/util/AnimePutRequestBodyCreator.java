package academy.devdojo.springboot2.util;

import academy.devdojo.springboot2.dto.AnimePutDto;

public class AnimePutRequestBodyCreator {

    public static AnimePutDto updateAnimePutDto(){
        return AnimePutDto.builder()
                .name(AnimeCreator.createAnimeToBeSaved().getName())
                .id(AnimeCreator.createAnimeToBeSaved().getId())
                .build();
    }
}
