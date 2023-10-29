package com.documentation.openapi.infrastructure;

import com.documentation.openapi.configuration.FileUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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

    private Path fileStorageLocation;

    @PostConstruct
    public void init() {
        try {
            fileStorageLocation = Paths.get(directoryPath).toAbsolutePath().normalize();
            Files.createDirectories(fileStorageLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not create directory.", e);
        }
    }

    public void uploadFile(MultipartFile file) {
        if (!FileUtil.isValidFileExtension(file.getOriginalFilename())) {
            throw new RuntimeException("Unsupported format.");
        }

        String originalFileName = Objects.requireNonNull(file.getOriginalFilename(), "File name cannot be null");
        Path targetLocation = fileStorageLocation.resolve(originalFileName);

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("An error occurred while storing the file " + originalFileName, e);
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
            Path filePath = fileStorageLocation.resolve(fileName);

            if (!Files.exists(filePath)) return "File not found.";

            Files.delete(filePath);
            return "File deleted successfully.";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
