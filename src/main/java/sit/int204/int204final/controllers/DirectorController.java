package sit.int204.int204final.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sit.int204.int204final.dtos.AddEditDirectorInput;
import sit.int204.int204final.entities.Director;
import sit.int204.int204final.services.DirectorService;

import java.util.List;

@RestController
@RequestMapping("/api/directors")
@AllArgsConstructor
public class DirectorController {
    private final DirectorService directorService;

    @PostMapping
    public ResponseEntity<Director> createDirector(@Valid @RequestBody AddEditDirectorInput input) {
        return ResponseEntity.status(HttpStatus.CREATED).body(directorService.createDirector(input));
    }

    @GetMapping
    public ResponseEntity<List<Director>> getAllDirectors(@RequestParam(defaultValue = "firstName") String sortBy,
                                                          @RequestParam(defaultValue = "asc") String order,
                                                          @RequestParam(required = false) String name) {
        Sort sort = Sort.by(Sort.Direction.fromString(order), sortBy);
        return ResponseEntity.ok(directorService.getAllDirectors(sort, name));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Director> getDirectorById(@PathVariable int id) {
        return ResponseEntity.ok(directorService.getDirectorById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Director> updateDirector(@PathVariable int id, @Valid @RequestBody AddEditDirectorInput input) {
        return ResponseEntity.ok(directorService.updateDirector(id, input));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDirector(@PathVariable int id) {
        directorService.deleteDirector(id);
        return ResponseEntity.noContent().build();
    }
}
