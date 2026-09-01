package academy.devdojo.springboot2.repository;

import academy.devdojo.springboot2.domain.Anime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

@DataJpaTest
@DisplayName("Tests for Anime Repository")
class IAnimeRepositoryTest {

    @Autowired
    private IAnimeRepository animeRepository;

    @Test
    @DisplayName("Save creates anime when Successful")
    //salvar_Persistente anime_Quando_bem-sucedido
    void save_PersistAnime_WhenSuccessful(){
        Anime animeToBeSaved = createAnime();
        Anime savedAnime = this.animeRepository.save(animeToBeSaved);
        Assertions.assertThat(savedAnime).isNotNull();// 3. Verifica se o objeto retornado pelo banco não é nulo (garantindo que a operação ocorreu)
        Assertions.assertThat(savedAnime.getId()).isNotNull();// 4. Verifica se o banco gerou e atribuiu um ID válido (diferente de nulo) para o novo registro
        Assertions.assertThat(savedAnime.getName()).isEqualTo(animeToBeSaved.getName());// 5. Garante que o nome do anime salvo no banco é exatamente igual ao nome do objeto original que enviamos

    }
    private Anime createAnime(){
        return Anime.builder()
                .name("Hajime no Ippo")
                .build();
    }
}