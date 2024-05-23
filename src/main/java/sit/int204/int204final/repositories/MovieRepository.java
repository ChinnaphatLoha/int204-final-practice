package sit.int204.int204final.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import sit.int204.int204final.entities.Movie;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
    Page<Movie> findAllByGenreName(String genre, Pageable pageable);
}