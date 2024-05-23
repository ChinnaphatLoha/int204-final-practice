package sit.int204.int204final.services;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sit.int204.int204final.dtos.AddEditDirectorInput;
import sit.int204.int204final.entities.Director;
import sit.int204.int204final.exceptions.NotFoundException;
import sit.int204.int204final.repositories.DirectorRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class DirectorService {
    private final DirectorRepository directorRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public Director createDirector(AddEditDirectorInput input) {
        Director director = modelMapper.map(input, Director.class);
        return directorRepository.save(director);
    }

    public List<Director> getAllDirectors(Sort sort, String name) {
        if (name != null) {
            return directorRepository.findAllByFullNameContaining(name, sort);
        }
        return directorRepository.findAll(sort);
    }

    public Director getDirectorById(int id) {
        return directorRepository.findById(id).orElseThrow(() -> new NotFoundException("Director not found"));
    }

    @Transactional
    public Director updateDirector(int id, AddEditDirectorInput input) {
        if (!directorRepository.existsById(id)) {
            throw new NotFoundException("Director not found");
        }
        Director director = modelMapper.map(input, Director.class);
        director.setId(id);
        return directorRepository.save(director);
    }

    @Transactional
    public void deleteDirector(int id) {
        directorRepository.deleteById(id);
    }
}
