package academy.devdojo.springboot2.controller;

import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.dto.AnimePostDto;
import academy.devdojo.springboot2.dto.AnimePutDto;
import academy.devdojo.springboot2.services.AnimeService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class AnimeControllerTest {
    @InjectMocks
    private AnimeController animeController;

    @Mock
    private AnimeService animeServiceMock;

    @Test
    @DisplayName("List returns list of anime inside page object when sucessful")
    void list_ReturnsListOfAnimeInsidePageObjetect_WhenSuccessfull(){
        String expectedName = AnimeCreator.createValidAnime().getName();
        PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createValidAnime()));

        // 1. Primeiro configuramos o mock (Stubbing)
        BDDMockito.when(animeServiceMock.listAll(ArgumentMatchers.any()))
                .thenReturn(animePage);

        // 2. Depois chamamos o método do controller
        Page<Anime> page = animeController.list(null).getBody();

        // 3. Por fim, fazemos as validações
        Assertions.assertThat(page).isNotNull();
        Assertions.assertThat(page.toList())
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(page.toList().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("ListAll returns list of anime when sucessful")
    void listAll_ReturnsListOfAnimes_WhenSuccessfull(){
        String expectedName = AnimeCreator.createValidAnime().getName();
        List<Anime> expectedAnimeList = List.of(AnimeCreator.createValidAnime());

        // 1. Primeiro configuramos o mock
        BDDMockito.when(animeServiceMock.listAllNonPageable())
                .thenReturn(expectedAnimeList);

        // 2. Depois chamamos o controller
        List<Anime> animeList = animeController.listAll().getBody();

        // 3. Validações
        Assertions.assertThat(animeList)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(animeList.get(0).getName()).isEqualTo(expectedName);
    }
    @Test
    @DisplayName("FindById returns anime when sucessful")
    void findById_ReturnsListOfAnimes_WhenSuccessfull(){
        Long expectedId = AnimeCreator.createValidAnime().getId();

        BDDMockito.when(animeServiceMock.findByIdOrThorwBadRequestExcepetion(ArgumentMatchers.anyLong()))
                .thenReturn(AnimeCreator.createValidAnime());

        Anime anime= animeController.findById(1L).getBody();

        // 3. Validações
        Assertions.assertThat(anime)
                .isNotNull();

        Assertions.assertThat(anime.getId()).isNotNull().isEqualTo(expectedId);
    }
    @Test
    @DisplayName("FindByName returns a list of anime when sucessful")
    void findByName_ReturnsListOfAnimes_WhenSuccessfull(){
        String expectedName = AnimeCreator.createValidAnime().getName();

        BDDMockito.when(animeServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(AnimeCreator.createValidAnime()));

        List<Anime> animes= animeController.findByName("anime").getBody();

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
        BDDMockito.when(animeServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Anime> animes= animeController.findByName("anime").getBody();

        // 3. Validações
        Assertions.assertThat(animes)
                .isNotNull()
                .isEmpty();

    }
    @Test
    @DisplayName("Save returns anime when successful")
    void save_ReturnsAnimes_WhenSuccessful(){
        BDDMockito.when(animeServiceMock.save(ArgumentMatchers.any(AnimePostDto.class)))
                .thenReturn(AnimeCreator.createValidAnime());

        Anime anime= animeController.save(AnimePostRequestBodyCreator.createAnimePostDto()).getBody();

        // 3. Validações
        Assertions.assertThat(anime).isNotNull().isEqualTo(AnimeCreator.createValidAnime());
    }
    @Test
    @DisplayName("Replace returns anime when successful")
    void replace_ReturnsAnimes_WhenSuccessful(){
        BDDMockito.doNothing().when(animeServiceMock).replace(ArgumentMatchers.any(AnimePutDto.class));

        ResponseEntity<Void> entity = animeController.replace(AnimePutRequestBodyCreator.updateAnimePutDto());


        // 3. Validações
        Assertions.assertThat(entity).isNotNull();

        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
    @Test
    @DisplayName("Delete returns anime when successful")
    void delete_RemovesAnimes_WhenSuccessful(){
        BDDMockito.doNothing().when(animeServiceMock).delete(ArgumentMatchers.anyLong());

        ResponseEntity<Void> entity = animeController.delete(1);


        // 3. Validações
        Assertions.assertThat(entity).isNotNull();

        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}