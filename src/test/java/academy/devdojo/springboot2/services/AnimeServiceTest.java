package academy.devdojo.springboot2.services;

import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.exception.BadRequestException;
import academy.devdojo.springboot2.repository.IAnimeRepository;
import academy.devdojo.springboot2.util.AnimeCreator;
import academy.devdojo.springboot2.util.AnimePostRequestBodyCreator;
import academy.devdojo.springboot2.util.AnimePutRequestBodyCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AnimeServiceTest {
    @InjectMocks
    private AnimeService animeService;

    @Mock
    private IAnimeRepository animeRepositoryMocke;

    @Test
    @DisplayName("ListAll returns list of anime inside page object when sucessful")
    void list_ReturnsListOfAnimeInsidePageObjetect_WhenSuccessfull(){
        String expectedName = AnimeCreator.createValidAnime().getName();
        PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createValidAnime()));

        // 1. Primeiro configuramos o mock (Stubbing)
        BDDMockito.when(animeRepositoryMocke.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(animePage);

        // 2. Depois chamamos o método do controller
        Page<Anime> page = animeService.listAll(PageRequest.of(1,1));

        // 3. Por fim, fazemos as validações
        Assertions.assertThat(page).isNotNull();
        Assertions.assertThat(page.toList())
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(page.toList().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("listAllNonPageable returns list of anime when sucessful")
    void listAll_ReturnsListOfAnimes_WhenSuccessfull(){
        String expectedName = AnimeCreator.createValidAnime().getName();
        List<Anime> expectedAnimeList = List.of(AnimeCreator.createValidAnime());

        // 1. Primeiro configuramos o mock
        BDDMockito.when(animeRepositoryMocke.findAll())
                .thenReturn(expectedAnimeList);

        // 2. Depois chamamos o controller
        List<Anime> animeList = animeService.listAllNonPageable();

        // 3. Validações
        Assertions.assertThat(animeList)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(animeList.get(0).getName()).isEqualTo(expectedName);
    }
    @Test
    @DisplayName("findByIdOrThorwBadRequestExcepetion returns anime when sucessful")
    void findByIdOrThorwBadRequestExcepetion_ReturnsListOfAnimes_WhenSuccessfull(){
        Long expectedId = AnimeCreator.createValidAnime().getId();

        BDDMockito.when(animeRepositoryMocke.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(AnimeCreator.createValidAnime()));

        Anime anime= animeService.findByIdOrThorwBadRequestExcepetion(1L);

        // 3. Validações
        Assertions.assertThat(anime)
                .isNotNull();

        Assertions.assertThat(anime.getId()).isNotNull().isEqualTo(expectedId);
    }
    @Test
    @DisplayName("findByIdOrThorwBadRequestExcepetion thorws BadRequestExcepetion when anime is not found")
    void findByIdOrThorwBadRequestExcepetion_thowrsBadRequestExcepetion_WhenAnimeIsNotFound(){
        Long expectedId = AnimeCreator.createValidAnime().getId();

        BDDMockito.when(animeRepositoryMocke.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> animeService.findByIdOrThorwBadRequestExcepetion(1L));

    }
    @Test
    @DisplayName("FindByName returns a list of anime when sucessful")
    void findByName_ReturnsListOfAnimes_WhenSuccessfull(){
        String expectedName = AnimeCreator.createValidAnime().getName();

        BDDMockito.when(animeRepositoryMocke.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(AnimeCreator.createValidAnime()));

        List<Anime> animes= animeService.findByName("anime");

        // 3. Validações
        Assertions.assertThat(animes)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);

    }
    @Test
    @DisplayName("FindByName returns an empty list of anime is not found ")
    void findByName_ReturnsEmptyListOfAnimes_WhenAnimeNotFound(){
        BDDMockito.when(animeRepositoryMocke.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Anime> animes= animeService.findByName("anime");

        // 3. Validações
        Assertions.assertThat(animes)
                .isNotNull()
                .isEmpty();

    }
    @Test
    @DisplayName("Save returns anime when successful")
    void save_ReturnsAnimes_WhenSuccessful(){
        BDDMockito.when(animeRepositoryMocke.save(ArgumentMatchers.any(Anime.class)))
                .thenReturn(AnimeCreator.createValidAnime());

        Anime anime= animeService.save(AnimePostRequestBodyCreator.createAnimePostDto());

        // 3. Validações
        Assertions.assertThat(anime).isNotNull().isEqualTo(AnimeCreator.createValidAnime());
    }
    @Test
    @DisplayName("Replace returns anime when successful")
    void replace_ReturnsAnimes_WhenSuccessful(){

       Assertions.assertThatCode(()-> animeService.replace(AnimePutRequestBodyCreator.updateAnimePutDto()))
               .doesNotThrowAnyException();

    }
    @Test
    @DisplayName("Delete returns anime when successful")
    void delete_RemovesAnimes_WhenSuccessful(){
        BDDMockito.doNothing().when(animeRepositoryMocke).delete(ArgumentMatchers.any(Anime.class));

        Assertions.assertThatCode(()-> animeService.delete(1L))
                .doesNotThrowAnyException();
    }
}
