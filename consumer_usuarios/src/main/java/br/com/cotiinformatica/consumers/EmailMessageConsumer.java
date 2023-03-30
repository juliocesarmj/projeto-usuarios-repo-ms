package br.com.cotiinformatica.consumers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.cotiinformatica.components.EmailMessageComponent;
import br.com.cotiinformatica.dtos.EmailMessageDTO;

@Service
public class EmailMessageConsumer {
	
	@Autowired
	private EmailMessageComponent emailMessageComponent;
	
	@RabbitListener(queues = {"${queue.name}"})
	public void receive(@Payload String payload) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		EmailMessageDTO emailMessageDTO = objectMapper.readValue(payload, EmailMessageDTO.class);
		
		emailMessageComponent.sendMessage(emailMessageDTO);
	}
	
}
