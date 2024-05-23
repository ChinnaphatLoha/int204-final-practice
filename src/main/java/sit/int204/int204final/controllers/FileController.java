package sit.int204.int204final.controllers;

import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sit.int204.int204final.services.FileService;

@RestController
@RequestMapping("/api/files")
@AllArgsConstructor
public class FileController {
    FileService fileService;

    @GetMapping("/init")
    public ResponseEntity<Object> testPropertiesMapping() {
        return ResponseEntity.ok(fileService.getFileStorageLocation()+ " has been created !!!");
    }

    @GetMapping("")
    public ResponseEntity<Object> getAllFiles() {
        return ResponseEntity.ok(fileService.getAllFiles());
    }

    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = fileService.loadFileAsResource(filename);
        String extension = filename.substring(filename.lastIndexOf(".")+1);
        switch (extension) {
            case "jpg", "jpeg", "png" -> {
                return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(file);
            }
            case "pdf" -> {
                return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(file);
            }
            case "json" -> {
                return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(file);
            }
            default -> {
                return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(file);
            }
        }
    }

    @PostMapping("")
    public ResponseEntity<Object> fileUpload(@RequestParam("file") MultipartFile file) {
        fileService.store(file);
        return ResponseEntity.ok("You successfully uploaded " + file.getOriginalFilename());
    }

    @DeleteMapping("/{filename:.+}")
    public ResponseEntity<String> deleteFile(@PathVariable String filename) {
        fileService.deleteFile(filename);
        return ResponseEntity.ok("File " + filename + " has been deleted !!!");
    }
}
