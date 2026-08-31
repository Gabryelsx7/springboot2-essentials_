package academy.devdojo.springboot2.client;

import academy.devdojo.springboot2.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class SpringClient {
    public static void main(String[] args) {
        ResponseEntity<Anime> entity = new RestTemplate().getForEntity("http://localhost:8080/animes/{id}", Anime.class,3);
        log.info(entity);

        Anime object = new RestTemplate().getForObject("http://localhost:8080/animes/{id}", Anime.class,3);
        log.info(object);


        //Spring Boot 2 Essentials 27 - RestTemplate exchange
        Anime[] animes = new RestTemplate().getForObject("http://localhost:8080/animes/all", Anime[].class);
        log.info(Arrays.toString(animes));

        //Get
        ResponseEntity<List<Anime>> exchange = new RestTemplate().exchange("http://localhost:8080/animes/all", HttpMethod.GET, null
                , new ParameterizedTypeReference<List<Anime>>() {});
        log.info(exchange.getBody());

        //Post
//        Anime kingdom = Anime.builder().name("Kingdod").build();
//        Anime kingdomSave = new RestTemplate().postForObject("http://localhost:8080/animes", kingdom, Anime.class);
//        log.info("Saved anime {}", kingdomSave);

        Anime samuraiChamplo = Anime.builder().name("samuraiChamplo").build();
        ResponseEntity<Anime> samuraiChamploSave = new RestTemplate().exchange("http://localhost:8080/animes",
                HttpMethod.POST,
                new HttpEntity<>(samuraiChamplo,createJson()), Anime.class);
        log.info("Saved anime {}", samuraiChamploSave);

    }
    private static HttpHeaders createJson(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        //httpHeaders.setBearerAuth();
        return httpHeaders;
    }
}
