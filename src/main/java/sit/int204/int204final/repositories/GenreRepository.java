package sit.int204.int204final.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sit.int204.int204final.entities.Genre;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
    boolean existsByName(String name);
}