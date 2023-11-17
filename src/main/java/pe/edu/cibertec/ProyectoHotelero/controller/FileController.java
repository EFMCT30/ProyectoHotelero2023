package pe.edu.cibertec.ProyectoHotelero.controller;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
@RequestMapping("/uploads")
public class FileController {
    private final Path fileStorageLocation = Paths.get("uploads/images");

//    @GetMapping("/images/{filename:.+}")
//    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
//        try {
//            Path filePath = fileStorageLocation.resolve(filename).normalize();
//            Resource resource = new UrlResource(filePath.toUri());
//
//            if (resource.exists()) {
//                return ResponseEntity.ok()
//                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
//                        .body(resource);
//            } else {
//                return ResponseEntity.notFound().build();
//            }
//        } catch (IOException ex) {
//            // Manejar excepciones según tus necesidades
//            return ResponseEntity.notFound().build();
//        }
//    }

    @GetMapping("/images/{filename:.+}")
    public ResponseEntity<byte[]> serveFile(@PathVariable String filename) {
        try {
            Path filePath = fileStorageLocation.resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                byte[] data = Files.readAllBytes(filePath);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG); // Set the appropriate content type

                return new ResponseEntity<>(data, headers, HttpStatus.OK);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException ex) {
            // Handle exceptions as needed
            return ResponseEntity.notFound().build();
        }
    }

}
