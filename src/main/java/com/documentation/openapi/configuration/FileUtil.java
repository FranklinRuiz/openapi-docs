package com.documentation.openapi.configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileUtil {
    public static List<File> getYamlFiles(String directoryPath) {
        File directory = new File(directoryPath);
        List<File> yamlFiles = new ArrayList<>();

        if (directory.exists() && directory.isDirectory()) {
            for (File file : Objects.requireNonNull(directory.listFiles())) {
                if (file.getName().endsWith(".yml") || file.getName().endsWith(".yaml")) {
                    yamlFiles.add(file);
                }
            }
        }

        return yamlFiles;
    }

    public static boolean isValidFileExtension(String filename) {
        if (filename == null) return false;

        String lowerCaseFilename = filename.toLowerCase();
        return lowerCaseFilename.endsWith(".yml") || lowerCaseFilename.endsWith(".yaml");
    }
}
