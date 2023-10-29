package com.documentation.openapi.presentation;

import com.documentation.openapi.configuration.swagger.SwaggerConfigService;
import com.documentation.openapi.infrastructure.SwaggerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.UncheckedIOException;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/swagger")
@RequiredArgsConstructor
public class SwaggerController {

    private final SwaggerConfigService swaggerConfigService;
    private final SwaggerService swaggerService;


    @GetMapping("/reload")
    public ResponseEntity<String> reloadSwaggerConfigurations() {
        try {
            swaggerConfigService.loadApiDocs();
            return ResponseEntity.ok("Swagger configurations reloaded.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error reloading Swagger configurations: " + e.getMessage());
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            swaggerService.uploadFile(file);
            swaggerConfigService.loadApiDocs();
            return ResponseEntity.ok("File uploaded successfully.");
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("The file could not be uploaded. Error: " + ex.getMessage());
        }
    }

    @GetMapping("/info")
    public ResponseEntity<List<String>> getFilesInfo() {
        try {
            List<String> filesInfo = swaggerService.getFilesInfo();
            return ResponseEntity.ok(filesInfo);
        } catch (UncheckedIOException ex) {
            return ResponseEntity.status(500).body(Collections.emptyList());
        }
    }

    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
        try {
            String result = swaggerService.deleteFile(fileName);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting file: " + e.getMessage());
        }
    }
}
