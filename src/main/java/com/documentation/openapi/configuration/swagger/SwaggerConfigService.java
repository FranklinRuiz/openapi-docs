package com.documentation.openapi.configuration.swagger;

import com.documentation.openapi.configuration.FileUtil;
import org.apache.commons.io.FilenameUtils;
import org.springdoc.core.models.GroupedOpenApi;
import org.springdoc.core.properties.SwaggerUiConfigParameters;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class SwaggerConfigService {

    @Value("${directory.apidocs}")
    private String directoryPath;

    private final SwaggerUiConfigParameters swaggerUiConfigParameters;
    private final List<GroupedOpenApi> groupedOpenApis;

    public SwaggerConfigService(SwaggerUiConfigParameters swaggerUiConfigParameters) {
        this.swaggerUiConfigParameters = swaggerUiConfigParameters;
        this.groupedOpenApis = new ArrayList<>();
    }

    public void loadApiDocs() {
        List<File> listOfFiles = FileUtil.getYamlFiles(directoryPath);

        groupedOpenApis.clear();

        for (File file : listOfFiles) {
            String groupName = FilenameUtils.getBaseName(file.getName());
            GroupedOpenApi group = GroupedOpenApi.builder()
                    .group(groupName)
                    .pathsToMatch("/v3/api-docs/" + groupName + "/**")
                    .build();
            groupedOpenApis.add(group);
            swaggerUiConfigParameters.addGroup(groupName);
        }
    }

    public List<GroupedOpenApi> getGroupedOpenApis() {
        this.loadApiDocs();
        return groupedOpenApis;
    }
}
