package com.documentation.openapi.infrastructure;

import com.documentation.openapi.configuration.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SwaggerService {

    @Value("${directory.apidocs}")
    private String directoryPath;

    public void uploadFile(MultipartFile file) {
        try {
            Path fileStorageLocation = Paths.get(directoryPath).toAbsolutePath().normalize();

            if (!Files.exists(fileStorageLocation)) {
                Files.createDirectories(fileStorageLocation);
            }

            Path targetLocation = fileStorageLocation.resolve(Objects.requireNonNull(file.getOriginalFilename()));
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getFilesInfo() {
        List<File> files = FileUtil.getYamlFiles(directoryPath);

        return files.stream()
                .map(File::getName)
                .collect(Collectors.toList());
    }

    public String deleteFile(String fileName) {
        try {
            Path filePath = Paths.get(directoryPath, fileName);

            if (!Files.exists(filePath)) return "File not found.";

            Files.delete(filePath);
            return "File deleted successfully.";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
