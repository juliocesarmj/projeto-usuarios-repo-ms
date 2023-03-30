package br.com.cotiinformatica.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import br.com.cotiinformatica.dtos.EmailMessageDTO;
import jakarta.mail.internet.MimeMessage;

@Component
public class EmailMessageComponent {
	
	@Autowired
	JavaMailSender javaMailSender;
	
	@Value("${spring.mail.username}")
	private String userName;
	
	public void sendMessage(EmailMessageDTO dto) throws Exception {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
		
		helper.setFrom(userName);
		helper.setTo(dto.getTo());
		helper.setSubject(dto.getSubject());
		helper.setText(dto.getBody());
		
		javaMailSender.send(mimeMessage);
	}
}
