package com.documentation.openapi.presentation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@RestController
@RequestMapping("/v3/api-docs")
public class OpenApiController {

    @Value("${directory.apidocs}")
    private String directoryPath;

    @GetMapping("/{groupName}")
    public ResponseEntity<Resource> getSpec(@PathVariable String groupName) {
        File file = new File(directoryPath + groupName + ".yaml");
        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        Resource resource = new FileSystemResource(file);
        return ResponseEntity.ok(resource);
    }
}
