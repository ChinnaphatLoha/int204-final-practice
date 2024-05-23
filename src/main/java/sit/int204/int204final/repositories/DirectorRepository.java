package sit.int204.int204final.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sit.int204.int204final.entities.Director;

import java.util.List;

public interface DirectorRepository extends JpaRepository<Director, Integer> {
    @Query("SELECT d FROM Director d WHERE CONCAT(d.firstName, ' ', d.lastName) LIKE %?1%")
    List<Director> findAllByFullNameContaining(String name, Sort sort);
}