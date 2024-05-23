package sit.int204.int204final.services;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import sit.int204.int204final.configs.FileStorageProperties;
import sit.int204.int204final.dtos.FileInfo;
import sit.int204.int204final.exceptions.InvalidFileNameException;
import sit.int204.int204final.exceptions.NotFoundException;
import sit.int204.int204final.exceptions.FileOperationException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@Getter
public class FileService {
    private final Path fileStorageLocation;

    @Autowired
    public FileService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties
                .getUploadDir()).toAbsolutePath().normalize();
        try {
            if (!Files.exists(this.fileStorageLocation)) {
                Files.createDirectories(this.fileStorageLocation);
            }
        } catch (IOException ex) {
            throw new FileOperationException(
                    "Could not create the directory where the uploaded files will be stored.");
        }

    }

    public String store(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new InvalidFileNameException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException ex) {
            throw new FileOperationException("Could not store file " + fileName + ". Please try again!");
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new NotFoundException("File not found: " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new FileOperationException("This file is not supported: " + fileName);
        }
    }

    public void deleteFile(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Files.delete(filePath);
        } catch (IOException ex) {
            throw new FileOperationException("Could not delete file: " + fileName);
        }
    }

    public String[] listAllFiles() {
        try (Stream<Path> paths = Files.list(this.fileStorageLocation)) {
            return paths.map(Path::getFileName).map(Path::toString).toArray(String[]::new);
        } catch (IOException ex) {
            throw new FileOperationException("Could not list files: " + ex.getMessage());
        }
    }

    public List<FileInfo> getAllFiles() {
        try (Stream<Path> paths = Files.list(this.fileStorageLocation)) {
            return paths.map(path -> {
                        FileInfo info = new FileInfo();
                        info.setFilename(path.getFileName().toString());
                        try {
                            info.setSize(Files.size(path));
                        } catch (IOException e) {
                            throw new FileOperationException("Could not read file size: " + e.getMessage());
                        }
                        info.setUrl(path.toUri().toString()); // Create URL (optional)
                        return info;
                    })
                    .toList();
        } catch (IOException ex) {
            throw new FileOperationException("Could not list files: " + ex.getMessage());
        }
    }
}
