package academy.devdojo.springboot2.controller;

import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.util.Dateutil;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("anime")
@Log4j2

@RequiredArgsConstructor
public class AnimeController {

    private final Dateutil dateutil;


    // s@RequestMapping(method = RequestMethod.GET, path = "list") localhost:8080/anime/list
    @GetMapping(path = "list")
    public List<Anime> list() {
        log.info(dateutil.formatLocalDateTimeToDataBaseStryle(LocalDateTime.now()));
        return List.of(new Anime("DBZ"),
                new Anime("Bersek"));
    }

    @GetMapping(path = "list2")
    public List<Anime> list2() {
        log.info(dateutil.formatLocalDateTimeToDataBaseStryle(LocalDateTime.now()));
        return List.of(new Anime("DBZ"),
                new Anime("One Piece2"));
    }
}
