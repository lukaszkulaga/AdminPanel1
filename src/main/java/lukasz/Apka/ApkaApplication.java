package lukasz.Apka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@ComponentScan
public class ApkaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApkaApplication.class, args);
	}
}
