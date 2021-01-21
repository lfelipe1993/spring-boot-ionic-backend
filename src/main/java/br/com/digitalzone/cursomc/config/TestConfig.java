package br.com.digitalzone.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.digitalzone.cursomc.services.DBService;
import br.com.digitalzone.cursomc.services.mail.EmailService;
import br.com.digitalzone.cursomc.services.mail.MockEmailService;

@Configuration
@Profile("test")
public class TestConfig {
	
	@Autowired
	
	private DBService dbService;
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		
		dbService.instantiateDatabaseService();
		return true;
	}
	
	@Bean
	public EmailService emailService() {
		return new MockEmailService();
	}
}
