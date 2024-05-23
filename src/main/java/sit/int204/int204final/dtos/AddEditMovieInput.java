package sit.int204.int204final.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AddEditMovieInput {
    private static final int MIN_RELEASE_YEAR = 1888;

    @NotEmpty(message = "Title is required")
    @Size(max = 100, message = "Title must not exceed 100 characters")
    private String title;

    @NotNull(message = "Release year is required")
    private Integer releaseYear;

    @NotNull(message = "Genre is required")
    private Integer genreId;

    @NotNull(message = "Director is required")
    private Integer directorId;

    public void setReleaseYear(Integer releaseYear) {
        int currentYear = LocalDate.now().getYear();
        if (releaseYear > currentYear) {
            throw new IllegalArgumentException("Release year must not be in the future");
        } else if (releaseYear < MIN_RELEASE_YEAR) {
            throw new IllegalArgumentException("Release year must not be earlier than " + MIN_RELEASE_YEAR);
        }
        this.releaseYear = releaseYear;
    }
}
