package org.ripreal.textclassifier2.storage;

import org.ripreal.textclassifier2.storage.config.DefaultProfileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
public class ServiceApp {

    private final static Logger log = LoggerFactory.getLogger(ServiceApp.class);

    public static void main(String... args) throws UnknownHostException {
        SpringApplication app = new SpringApplication(ServiceApp.class);
        DefaultProfileUtil.addDefaultProfile(app);
        Environment env = app.run(args).getEnvironment();

        String protocol = "http";
        log.info("\n----------------------------------------------------------\n\t" +
                    "Application '{}' is running! Access URLs:\n\t" +
                    "Local: \t\t{}://localhost:{}\n\t" +
                    "External: \t{}://{}:{}\n\t" +
                    "Profile(s): \t{}\n----------------------------------------------------------",
            env.getProperty("spring.application.name"),
            protocol,
            env.getProperty("server.port"),
            protocol,
            InetAddress.getLocalHost().getHostAddress(),
            env.getProperty("server.port"),
            env.getActiveProfiles());
    }
}

