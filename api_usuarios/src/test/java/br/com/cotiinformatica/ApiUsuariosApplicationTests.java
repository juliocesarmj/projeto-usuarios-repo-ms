package br.com.cotiinformatica;

import static org.assertj.core.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

import br.com.cotiinformatica.application.dtos.CriarContaDTO;

@SpringBootTest
@AutoConfigureMockMvc
class ApiUsuariosApplicationTests {

	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
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
		
	}
	
	@Test
	void atualizarDadosTest() throws Exception {
		fail("nao implementado");
	}
	
	@Test
	void autenticarTest() throws Exception {
		fail("nao implementado");
	}
	
	@Test
	void recuperarSenhaTest() throws Exception {
		fail("nao implementado");
	}

}
