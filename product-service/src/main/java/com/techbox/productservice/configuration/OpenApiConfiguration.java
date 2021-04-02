package com.techbox.productservice.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * This class generate bean for load basic info to apidoc
 */
@Configuration
public class OpenApiConfiguration {

    @Value("${api.info.title}")
    private String title;

    @Value("${api.info.description}")
    private String description;

    @Value("${api.info.version}")
    private String version;

    @Value("${api.info.license.name}")
    private String licenseName;

    @Value("${api.info.license.url}")
    private String licenseUrl;

    @Value("${api.info.contact.email}")
    private String contactEmail;

    @Value("${api.info.contact.name}")
    private String contactName;

    @Value("${api.info.contact.url}")
    private String contactUrl;

    @Value("${api.server.url}")
    private String serverUrl;

    @Value("${api.server.description}")
    private String serverDescription;

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
            .info(
                new Info()
                    .title(title)
                    .description(description)
                    .version(version)
                    .license(
                        new License()
                            .name(licenseName)
                            .url(licenseUrl))
                    .contact(
                        new Contact()
                            .email(contactEmail)
                            .name(contactName)
                            .url(contactUrl)))
            .servers(getServers());
    }

    private List<Server> getServers() {
        List<Server> servers = new ArrayList<>();
        servers.add(new Server().url(serverUrl).description(serverDescription));
        return servers;
    }
}