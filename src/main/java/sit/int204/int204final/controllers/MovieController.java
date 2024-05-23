package sit.int204.int204final.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sit.int204.int204final.dtos.AddEditMovieInput;
import sit.int204.int204final.entities.Movie;
import sit.int204.int204final.services.MovieService;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@AllArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @PostMapping
    public ResponseEntity<Movie> createMovie(@Valid @RequestBody AddEditMovieInput input) {
        return ResponseEntity.ok(movieService.createMovie(input));
    }

    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies(@RequestParam(defaultValue = "title") String sortBy,
                                                    @RequestParam(defaultValue = "asc") String order,
                                                    @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size,
                                                    @RequestParam(required = false) String genre) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), sortBy));
        return ResponseEntity.ok(movieService.getAllMovies(pageable, genre).getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable int id) {
        return ResponseEntity.ok(movieService.getMovieById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable int id, @Valid @RequestBody AddEditMovieInput input) {
        return ResponseEntity.ok(movieService.updateMovie(id, input));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable int id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }
}
