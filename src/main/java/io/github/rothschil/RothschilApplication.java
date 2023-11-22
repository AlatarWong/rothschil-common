package io.github.rothschil;

import io.github.rothschil.config.Version;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RothschilApplication {

	public static void main(String[] args) {
		SpringApplication.run(RothschilApplication.class, args);
		Version.printlnVersionInfo();
	}

}
