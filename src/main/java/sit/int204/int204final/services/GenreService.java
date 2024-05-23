package sit.int204.int204final.services;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sit.int204.int204final.entities.Genre;
import sit.int204.int204final.exceptions.NotFoundException;
import sit.int204.int204final.repositories.GenreRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class GenreService {
    private final GenreRepository genreRepository;

    @Transactional
    public Genre createGenre(String name) {
        if (Boolean.TRUE.equals(genreRepository.existsByName(name))) {
            throw new NotFoundException("Genre already exists");
        }
        Genre genre = new Genre();
        genre.setName(name);
        return genreRepository.save(genre);
    }

    public List<Genre> getAllGenres(Sort sort) {
        return genreRepository.findAll(sort);
    }

    public Genre getGenreById(int id) {
        return genreRepository.findById(id).orElseThrow(() -> new NotFoundException("Genre not found"));
    }

    @Transactional
    public Genre updateGenre(int id, Genre genre) {
        if (Boolean.TRUE.equals(genreRepository.existsByName(genre.getName()))) {
            throw new NotFoundException("Genre already exists");
        }
        Genre existingGenre = genreRepository.findById(id).orElseThrow(() -> new NotFoundException("Genre not found"));
        existingGenre.setName(genre.getName());
        return genreRepository.save(existingGenre);
    }

    @Transactional
    public void deleteGenre(int id) {
        genreRepository.deleteById(id);
    }
}
