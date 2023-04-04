package br.com.cotiinformatica;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

import br.com.cotiinformatica.application.dtos.AtualizarDadosDTO;
import br.com.cotiinformatica.application.dtos.AutenticarDTO;
import br.com.cotiinformatica.application.dtos.AutenticarResponseDTO;
import br.com.cotiinformatica.application.dtos.CriarContaDTO;
import br.com.cotiinformatica.application.dtos.RecuperarSenhaDTO;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ApiUsuariosApplicationTests {

	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private static String id;
	private static String email;
	private static String senha;
	private static String accessToken;

	@Order(1)
	@Test
	void criarContaTest() throws Exception {
		CriarContaDTO dto = new CriarContaDTO();
		Faker faker = new Faker();
		dto.setNome(faker.name().fullName());
		dto.setEmail(faker.internet().emailAddress());
		dto.setSenha("@Admin12312");
		
		mockMvc.perform(
				 post("/api/usuarios/criar-conta")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto))
				).andExpect(status().isCreated());
		
		email = dto.getEmail();
		senha = dto.getSenha();
	}
	
	@Order(2)
	@Test
	void autenticarTest() throws Exception {
		
		AutenticarDTO dto = new AutenticarDTO();
		dto.setEmail(email);
		dto.setSenha(senha);
		
		MvcResult result = mockMvc.perform(
				 post("/api/usuarios/autenticar")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto))
				).andExpect(status().isOk()).andReturn();
		
		AutenticarResponseDTO response = objectMapper
				.readValue(result.getResponse().getContentAsString(), AutenticarResponseDTO.class);
		
		id = response.getId();
		accessToken = response.getAccessToken();
		
		Assertions.assertNotNull(response.getAccessToken());
		Assertions.assertEquals(email, response.getEmail());
		
	}
	
	@Order(3)
	@Test
	void atualizarDadosTest() throws Exception {
		AtualizarDadosDTO dto = new AtualizarDadosDTO();
		
		Faker faker = new Faker();
		dto.setId(id);
		dto.setNome(faker.name().fullName());
		dto.setSenha("@Teste1234");
		
		mockMvc.perform(put("/api/usuarios/atualizar-dados")
				.header("Authorization", "Bearer " + accessToken)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto)))
				.andExpect(status().isOk());
	}
	
	@Order(4)
	@Test
	void recuperarSenhaTest() throws Exception {
		
		RecuperarSenhaDTO dto = new RecuperarSenhaDTO();	
		dto.setEmail(email);
		
		mockMvc.perform(post("/api/usuarios/recuperar-senha")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto)))
				.andExpect(status()
				.isOk());
	}

}
