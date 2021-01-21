package br.com.digitalzone.cursomc.services.mail;

import org.springframework.mail.SimpleMailMessage;

import br.com.digitalzone.cursomc.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
	

}
