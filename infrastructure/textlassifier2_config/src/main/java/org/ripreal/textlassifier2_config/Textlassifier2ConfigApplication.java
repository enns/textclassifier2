package org.ripreal.textlassifier2_config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableConfigServer
@EnableEurekaClient
public class Textlassifier2ConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(Textlassifier2ConfigApplication.class, args);
	}
}
