package com.lirou.store.services;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.ws.rs.BadRequestException;


@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
class SuperFreteServiceTest {

	@InjectMocks
	private SuperFreteService superFreteService;
	
	@Test
	@DisplayName("temporario")
	void test() throws Exception {
		
	}
	
	
//	@Test
//	@DisplayName("deveria calcular frete")
//	void deveriaCalcularFrete() throws Exception {
//		
//		//arrange
//		String id = "12121221";
//		BDDMockito.given(superFreteService.calculateShipping(id)).willReturn(new ArrayList<>());
//		
//		//assert
//		Assertions.assertEquals( new ArrayList<>(), superFreteService.calculateShipping("123"));
//	}
//	
//	@Test
//	@DisplayName("deveria estourar exception")
//	void nãoDeveriaCalcularFrete() throws Exception {
//		//arrange
//		String id = null;
//		BDDMockito.given(superFreteService.calculateShipping(id)).willThrow(BadRequestException.class);
//		
//		//assert
//		Assertions.assertThrows( BadRequestException.class,() -> superFreteService.calculateShipping("123"));
//	}

}
