package academy.devdojo.springboot2.services;


import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.dto.AnimeDto;
import academy.devdojo.springboot2.dto.AnimePutDto;
import academy.devdojo.springboot2.repository.IAnimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimeService {

    private final IAnimeRepository animeRepository;
    private static List<Anime> animes;

    static {
        animes = new ArrayList<>(List.of(new Anime(1L, "DBZ"), new Anime(2L, "Bersek")));
    }


    public List<Anime> listAll() {
        return animeRepository.findAll();
    }

    public Anime findByIdOrThorwBadRequestExcepetion(Long id) {
        return animeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Anime não encontrado "));

    }

    public Anime save(AnimeDto animeDto) {
        Anime anime = Anime.builder()
                .name(animeDto.getName())
                .build();

        return animeRepository.save(anime);

    }

    public void delete(Long id) {
        animes.remove(findByIdOrThorwBadRequestExcepetion(id));
    }

    public void replace(AnimePutDto animePutDto) {
        findByIdOrThorwBadRequestExcepetion(animePutDto.getId());
        Anime anime = Anime.builder()
                .id(animePutDto.getId())
                .name(animePutDto.getName())
                .build();

        animeRepository.save(anime);
    }
}
