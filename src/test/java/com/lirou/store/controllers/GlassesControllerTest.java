package com.lirou.store.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import com.lirou.store.glasses.application.service.GlassesApplicationService;

import jakarta.ws.rs.core.MediaType;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class GlassesControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private GlassesApplicationService glassesApplicationService;
	
	// Testando método de modificar a disponibilidade
	@Test
	void deveriaModificarADisponibilidade() throws Exception {
		// Arrange
		String availability = """
				{
				  "available": "true"
				 }
				""";
		String id = "1010";
		
		// Act
		 MockHttpServletResponse response = mvc.perform(patch("/api/glasses/{identifier}", id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(availability)
				).andReturn().getResponse();
		
		// Assert
		Assertions.assertEquals(202, response.getStatus());
	}
	
	@Test
	void naoDeveriaModificarADisponibilidade() throws Exception {
		// Arrange
		String availability = "{}";
		String id = "1010";
		
		// Act
		 MockHttpServletResponse response = mvc.perform(patch("/api/glasses/{identifier}", id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(availability)
				).andReturn().getResponse();
		
		// Assert
		Assertions.assertEquals(400, response.getStatus());
	}
	
	
	// Método de salvar no banco
	@Test
	void naoDeveriaSalvarNoBanco() throws Exception {
		// Arrange
		String availability = "{}";
		
		// Act
		 MockHttpServletResponse response = mvc.perform(post("/api/glasses")
				.contentType(MediaType.APPLICATION_JSON)
				.content(availability)
				).andReturn().getResponse();
		
		// Assert
		Assertions.assertEquals(400, response.getStatus());
	}
	
	@Test
	void deveriaSalvarNoBanco() throws Exception {
		// Arrange
		String availability = """
			{
			  "title": "Juliet X Metal Vermelho -1",
			  "pic": "https://cdn.shopify.com/s/files/1/0057/4288/products/RB2140_622_125_54_003_1000x.jpg?v=1622048183",
			  "inStock": true,
			  "model": "Juliet X Metal",
			  "color": "Vermelho",
			  "brand": "Oalkey",
			  "price": 129
			}
			""";
		
		// Act
		 MockHttpServletResponse response = mvc.perform(post("/api/glasses")
				.contentType(MediaType.APPLICATION_JSON)
				.content(availability)
				).andReturn().getResponse();
		
		// Assert
		Assertions.assertEquals(201, response.getStatus());
	}

}
