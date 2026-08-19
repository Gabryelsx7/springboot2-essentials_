package academy.devdojo.springboot2.services;


import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.repository.IAnimeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AnimeService {

   private static List<Anime> animes;

   static {
    animes = new ArrayList<>(List.of(new Anime(1L,"DBZ"),new Anime(2L,"Bersek")));
   }

    private IAnimeRepository animeRepository;

    public List<Anime> listAll(){
        return List.of(new Anime(1L,"DBZ"),
                new Anime(2L,"Bersek"));
    }

    public Anime findById(Long id){
    return  animes.stream()
            .filter(anime -> anime.getId().equals(id))
            .findFirst()
            .orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST, "Anime não encontrado "));

    }
    public Anime save(Anime anime){
        anime.setId(ThreadLocalRandom.current().nextLong(3,3000));
        animes.add(anime);
        return anime;
    }

    public void delete(Long id){
       animes.remove(findById(id));
    }
    public void replace(Anime anime){
        delete(anime.getId());
        animes.add(anime);
    }
}
