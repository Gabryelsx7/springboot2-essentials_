package academy.devdojo.springboot2.repository;

import academy.devdojo.springboot2.domain.Anime;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@DisplayName("Tests for Anime Repository")
@Log4j2
class IAnimeRepositoryTest {

    @Autowired
    private IAnimeRepository animeRepository;

    @Test
    @DisplayName("Save persists anime when Successful")
    //salvar_Persistente anime_Quando_bem-sucedido
    void save_PersistAnime_WhenSuccessful(){
        Anime animeToBeSaved = createAnime();
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);
        Assertions.assertThat(animeSaved).isNotNull();// 3. Verifica se o objeto retornado pelo banco não é nulo (garantindo que a operação ocorreu)
        Assertions.assertThat(animeSaved.getId()).isNotNull();// 4. Verifica se o banco gerou e atribuiu um ID válido (diferente de nulo) para o novo registro
        Assertions.assertThat(animeSaved.getName()).isEqualTo(animeToBeSaved.getName());// 5. Garante que o nome do anime salvo no banco é exatamente igual ao nome do objeto original que enviamos
    }
    @Test
    @DisplayName("Save update anime when Successful")
    void save_UpdateAnime_WhenSuccessful(){
        Anime animeToBeSaved = createAnime();

        Anime animeSaved = this.animeRepository.save(animeToBeSaved);

        animeSaved.setName("Overload");

        Anime animeUpdate = this.animeRepository.save(animeSaved);

        Assertions.assertThat(animeUpdate).isNotNull();

        Assertions.assertThat(animeUpdate.getId()).isNotNull();

        Assertions.assertThat(animeUpdate.getName()).isEqualTo(animeSaved.getName());
    }

    @Test
    @DisplayName("Delete removes anime when Successful")
    void delete_RemovesAnime_WhenSuccessful(){
        Anime animeToBeSaved = createAnime();

        Anime animeSaved = this.animeRepository.save(animeToBeSaved);

        this.animeRepository.delete(animeSaved);

        Optional<Anime> animeOptional = this.animeRepository.findById(animeSaved.getId());

        Assertions.assertThat(animeOptional).isEmpty();

    }

    @Test
    @DisplayName("Find By Name return list of anime when Successful")
    void findByName_ReturnListOfAnime_WhenSuccessful(){
        Anime animeToBeSaved = createAnime();

        Anime animeSaved = this.animeRepository.save(animeToBeSaved);

        String name = animeSaved.getName();

        List<Anime> animes = this.animeRepository.findByName(name);


        Assertions.assertThat(animes).isNotEmpty();
        Assertions.assertThat(animes).contains(animeSaved);

    }

    @Test
    @DisplayName("Find By Name return empty list of anime when no anime is found")
    void findByName_ReturnEmptyList_WhenAnimeNotFound(){
        List<Anime> animes = this.animeRepository.findByName("xaxa");
        Assertions.assertThat(animes).isEmpty();

    }
    @Test
    @DisplayName("Save thorow ConstraintViolation when name is empty")
    void save_ThorowConstraintViolation_WhenNameIsEmpty(){
        Anime anime = new Anime();

      //  Assertions.assertThatThrownBy(() ->this.animeRepository.save(anime))
      //          .isInstanceOf(ConstraintViolation.class);
//
        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.animeRepository.save(anime))
                .withMessageContaining("The anime name cannot be empty");
    }

    private Anime createAnime(){
        return Anime.builder()
                .name("Hajime no Ippo")
                .build();
    }
}