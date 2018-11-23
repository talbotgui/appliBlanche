package com.guillaumetalbot.applicationblanche.rest.securite;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Value("${security.restcontroleur.suffixe}")
	private String controleursRestSuffix;

	@Override
	public void addInterceptors(final InterceptorRegistry registry) {
		registry.addInterceptor(new IntercepteurDesRessourcesAutorisees(this.controleursRestSuffix));
	}
}
