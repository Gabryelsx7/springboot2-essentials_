package academy.devdojo.springboot2.util;

import academy.devdojo.springboot2.dto.AnimePostDto;

public class AnimePostRequestBodyCreator {

    public static AnimePostDto createAnimePostDto(){
        return AnimePostDto.builder()
                .name(AnimeCreator.createAnimeToBeSaved().getName())
                .build();
    }
}
