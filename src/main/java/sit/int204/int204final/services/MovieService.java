package sit.int204.int204final.services;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sit.int204.int204final.dtos.AddEditMovieInput;
import sit.int204.int204final.entities.Movie;
import sit.int204.int204final.exceptions.NotFoundException;
import sit.int204.int204final.repositories.MovieRepository;

@Service
@AllArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final GenreService genreService;
    private final DirectorService directorService;
    private final ModelMapper modelMapper;

    @Transactional
    public Movie createMovie(AddEditMovieInput input) {
        Movie movie = modelMapper.map(input, Movie.class);
        movie.setGenre(genreService.getGenreById(input.getGenreId()));
        movie.setDirector(directorService.getDirectorById(input.getDirectorId()));
        return movieRepository.save(movie);
    }

    public Page<Movie> getAllMovies(Pageable pageable, String genre) {
        if (genre != null) {
            return movieRepository.findAllByGenreName(genre, pageable);
        }
        return movieRepository.findAll(pageable);
    }

    public Movie getMovieById(int id) {
        return movieRepository.findById(id).orElseThrow(() -> new NotFoundException("Movie not found"));
    }

    @Transactional
    public Movie updateMovie(int id, AddEditMovieInput input) {
        if (!movieRepository.existsById(id)) {
            throw new NotFoundException("Movie not found");
        }
        Movie movie = modelMapper.map(input, Movie.class);
        movie.setId(id);
        movie.setGenre(genreService.getGenreById(input.getGenreId()));
        movie.setDirector(directorService.getDirectorById(input.getDirectorId()));
        return movieRepository.save(movie);
    }

    @Transactional
    public void deleteMovie(int id) {
        movieRepository.deleteById(id);
    }
}
