package academy.devdojo.springboot2.controller;

import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.services.AnimeService;
import academy.devdojo.springboot2.util.Dateutil;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("anime")
@Log4j2

@RequiredArgsConstructor
public class AnimeController {

    private final Dateutil dateutil;
    private final AnimeService animeService;


    // s@RequestMapping(method = RequestMethod.GET, path = "list") localhost:8080/anime/list
    @GetMapping
    public ResponseEntity<List<Anime>> listAll() {
        log.info(dateutil.formatLocalDateTimeToDataBaseStryle(LocalDateTime.now()));
        return new ResponseEntity<>(animeService.listAll() , HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Anime> findById(@PathVariable Long id) {
        return  ResponseEntity.ok(animeService.findById(id));
    }

    @PostMapping
    @ResponseStatus( HttpStatus.CREATED)
    public ResponseEntity<Anime> save(@RequestBody Anime anime){
        return ResponseEntity.ok(animeService.save(anime));
    }
}
