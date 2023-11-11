package pe.edu.cibertec.ProyectoHotelero.util;

import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ImageManager {

    public boolean storeFile(MultipartFile file) throws Exception {
        boolean result = false;
        try {
            File resourcesDirectory = ResourceUtils.getFile("uploads/images/");
            String fileName = file.getOriginalFilename();
            Path targetLocation = Path.of(resourcesDirectory.getAbsolutePath(), fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            result = true;
        } catch (UncheckedIOException ex) {
            System.out.println("Error al eliminar archivo temporal:"+ ex);
        } catch (IOException ex) {
            throw new RuntimeException("Error al almacenar el archivo", ex);
        }
        return result;
    }
}
