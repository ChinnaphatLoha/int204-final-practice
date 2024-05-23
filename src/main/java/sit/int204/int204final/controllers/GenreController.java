package sit.int204.int204final.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sit.int204.int204final.entities.Genre;
import sit.int204.int204final.services.GenreService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/genres")
@AllArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @PostMapping
    public ResponseEntity<Genre> createGenre(@RequestBody Map<String, String> body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(genreService.createGenre(body.get("name")));
    }

    @GetMapping
    public ResponseEntity<List<Genre>> getAllGenres(@RequestParam(defaultValue = "name") String sortBy,
                                                    @RequestParam(defaultValue = "asc") String order) {
        Sort sort = Sort.by(Sort.Direction.fromString(order), sortBy);
        return ResponseEntity.ok(genreService.getAllGenres(sort));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Genre> getGenreById(@PathVariable int id) {
        return ResponseEntity.ok(genreService.getGenreById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Genre> updateGenre(@PathVariable int id, @RequestBody Genre genre) {
        return ResponseEntity.ok(genreService.updateGenre(id, genre));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenre(@PathVariable int id) {
        genreService.deleteGenre(id);
        return ResponseEntity.noContent().build();
    }
}
