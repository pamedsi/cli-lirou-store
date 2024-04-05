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

import com.lirou.store.glasses.application.service.GlassesService;

import jakarta.ws.rs.core.MediaType;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class GlassesControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private GlassesService glassesService;
	
	//testando metodo de modificar a disponibilidade
	@Test
	void deveriaModificarAdisponibilidade() throws Exception {
		//arrange
		String availability = """
				{
				  "available": "true"
				 }
				""";
		String id = "1010";
		
		//act
		 MockHttpServletResponse response = mvc.perform(patch("/api/glasses/{identifier}", id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(availability)
				).andReturn().getResponse();
		
		//assert
		Assertions.assertEquals(200, response.getStatus());
	}
	
	@Test
	void nãoDeveriaModificarAdisponibilidade() throws Exception {
		//arrange
		String availability = "{}";
		String id = "1010";
		
		//act
		 MockHttpServletResponse response = mvc.perform(patch("/api/glasses/{identifier}", id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(availability)
				).andReturn().getResponse();
		
		//assert
		Assertions.assertEquals(400, response.getStatus());
	}
	
	
	// metodo de salvar no banco
	@Test
	void nãoDeveriaSalvarNoBanco() throws Exception {
		//arrange
		String availability = "{}";
		
		//act
		 MockHttpServletResponse response = mvc.perform(post("/api/glasses")
				.contentType(MediaType.APPLICATION_JSON)
				.content(availability)
				).andReturn().getResponse();
		
		//assert
		Assertions.assertEquals(400, response.getStatus());
	}
	
	@Test
	void deveriaSalvarNoBanco() throws Exception {
		//arrange
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
		
		//act
		 MockHttpServletResponse response = mvc.perform(post("/api/glasses")
				.contentType(MediaType.APPLICATION_JSON)
				.content(availability)
				).andReturn().getResponse();
		
		//assert
		Assertions.assertEquals(201, response.getStatus());
	}

}
