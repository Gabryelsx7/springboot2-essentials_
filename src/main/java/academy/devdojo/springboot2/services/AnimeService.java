package academy.devdojo.springboot2.services;

import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.dto.AnimePostDto;
import academy.devdojo.springboot2.dto.AnimePutDto;
import academy.devdojo.springboot2.exception.BadRequestException;
import academy.devdojo.springboot2.mapper.AnimeMapper;
import academy.devdojo.springboot2.repository.IAnimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimeService {

    private final IAnimeRepository animeRepository;
    private final AnimeMapper animeMapper;

    public Page<Anime> listAll(Pageable pageable) {
        return animeRepository.findAll(pageable);
    }

    public Anime findByIdOrThorwBadRequestExcepetion(Long id) {
        return animeRepository.findById(id)
                .orElseThrow(() ->
                        new BadRequestException("Anime não encontrado ")
                );
    }

    public Anime save(AnimePostDto animePostDto) {
        return animeRepository.save(
                animeMapper.toAnime(animePostDto) // 2. USANDO O MAPPER INJETADO (sem .INSTANCE)
        );
    }

    public void delete(Long id) {
        Anime anime = findByIdOrThorwBadRequestExcepetion(id);
        animeRepository.delete(anime); // (Nota: corrigi aqui para deletar do banco JPA em vez da lista estática antiga)
    }

    public void replace(AnimePutDto animePutDto) {
        Anime savedAnime =
                findByIdOrThorwBadRequestExcepetion(animePutDto.getId());

        Anime anime =
                animeMapper.toAnime(animePutDto); // 3. USANDO O MAPPER INJETADO (sem .INSTANCE)

        anime.setId(savedAnime.getId());

        animeRepository.save(anime);
    }

    public List<Anime> findByName(String name) {
        return animeRepository.findByName(name);
    }
}