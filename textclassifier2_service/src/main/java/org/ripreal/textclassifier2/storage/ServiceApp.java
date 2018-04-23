package org.ripreal.textclassifier2.storage;

import org.ripreal.textclassifier2.storage.config.DefaultProfileUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServiceApp {
    public static void main(String... args) {
        SpringApplication app = new SpringApplication(ServiceApp.class);
        DefaultProfileUtil.addDefaultProfile(app);
        app.run(args);
    }
}

