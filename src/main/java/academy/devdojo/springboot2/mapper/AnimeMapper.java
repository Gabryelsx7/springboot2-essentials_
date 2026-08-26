package academy.devdojo.springboot2.mapper;

import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.dto.AnimePostDto;
import academy.devdojo.springboot2.dto.AnimePutDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnimeMapper {

    // Converte o DTO de criar para a entidade Anime
    Anime toAnime(AnimePostDto animePostDto);

    // Converte o DTO de atualizar para a entidade Anime
    Anime toAnime(AnimePutDto animePutDto);
}


