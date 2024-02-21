package com.lirou.store.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import com.google.gson.Gson;
import com.lirou.store.models.superfrete.ProtocolData;
import com.lirou.store.models.superfrete.shippingInfToSendToSuperFrete.ShippingInfToSendToSuperFreteDTO;
import com.lirou.store.services.SuperFreteService;

import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.core.MediaType;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class SuperFreteControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private SuperFreteService superFreteService;
	
	
	//****************************************** calcular frete
	@Test
	void deveriaCalcularFrete() throws Exception {
		
		//arrange
		String id = "12121221";
		BDDMockito.given(superFreteService.calculateShipping(id)).willReturn(new ArrayList<>());
		//act
		 MockHttpServletResponse response = mvc.perform(get("/api/shipping/calculate/{identifier}", id)
					.contentType(MediaType.APPLICATION_JSON)
					).andReturn().getResponse();
		 
		//assert
		Assertions.assertEquals(200, response.getStatus());
	}
	
	@Test
	@DisplayName("deveria causar bad request 400")
	void naoDeveriaCalcularFrete() throws Exception {
		
		//arrange
		String id = "12121221";
		BDDMockito.given(superFreteService.calculateShipping(id)).willThrow(BadRequestException.class);
		//act
		 MockHttpServletResponse response = mvc.perform(get("/api/shipping/calculate/{identifier}", id)
					.contentType(MediaType.APPLICATION_JSON)
					).andReturn().getResponse();
		 
		//assert
			Assertions.assertEquals(400, response.getStatus());
	}

	//****************************************** enviar produto
	@Test
	void deveriaEnviarProduto() throws Exception {
		
		//arrange
		String body ="""
				{
				  "platform": "SEDEX",
				  "from": {"name":"teste"},
				  "to": {"name":"teste"},
				  "service": 1,
				  "products": [{"name":"teste"}],
				  "volumes":{"height":1.5}
				}
				""";

		//act
		 MockHttpServletResponse response = mvc.perform(post("/api/shipping/send-to-superfrete")
					.contentType(MediaType.APPLICATION_JSON)
					.content(body)
					).andReturn().getResponse();
		 
		//assert
			Assertions.assertEquals(200, response.getStatus());
	}
	
	@Test
	@DisplayName("não deveria entrar na rota")
	void naoDeveriaEnviarProduto() throws Exception {
		
		//arrange
		String body ="""
				{}
				""";
		//act
		 MockHttpServletResponse response = mvc.perform(post("/api/shipping/send-to-superfrete")
					.contentType(MediaType.APPLICATION_JSON)
					.content(body)
					).andReturn().getResponse();
		 
		//assert
			Assertions.assertEquals(400, response.getStatus());
	}
}
