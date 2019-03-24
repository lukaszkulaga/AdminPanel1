package lukasz.Apka.configuration;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
 *  konfiguracja web
 * 
 * */

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Bean
	public BCryptPasswordEncoder pwdEncrypt() {
		BCryptPasswordEncoder bcp = new BCryptPasswordEncoder();
		return bcp;
	}

	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setMaxFileSize("5MB");
		factory.setMaxRequestSize("5MB"); // to co moze przyjsc ze strony jsp z formularza
		return factory.createMultipartConfig();
	}

}
