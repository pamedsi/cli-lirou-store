package com.lirou.store.services;

import com.lirou.store.superfrete.application.api.models.OrderInfoFromCustomer;
import com.lirou.store.superfrete.application.api.models.OrderService;
import com.lirou.store.superfrete.application.api.models.ProtocolData;
import com.lirou.store.superfrete.application.api.models.shippingInfToSendToSuperFrete.ProductInfo;
import com.lirou.store.superfrete.application.api.models.shippingInfToSendToSuperFrete.SuperFreteAddress;
import com.lirou.store.superfrete.application.service.SuperFreteApplicationService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.lirou.store.handler.exceptions.BadRequestExceptions;
import com.lirou.store.superfrete.application.api.models.AbortingRequest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;


@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SuperFreteApplicationServiceTest {

	@Autowired
	private SuperFreteApplicationService superFreteApplicationService;
	
	@Value("${CEP}")
	private String from;

	@Value("${SUPER_FRETE_URL}")
	private String baseURL;

	private static AbortingRequest abortingRequestDTO;
	
	//************** Método calcular frete
	@Test
	@DisplayName("Deveria calcular frete")
	void deveriaCalcularFrete() throws Exception {
		//assert
        Assertions.assertFalse(superFreteApplicationService.calculateShipping("01140080").isEmpty());
	}
	
	@Test
	@DisplayName("deveria estourar exception")
	void naoDeveriaCalcularFrete() {
		// Assert
		Assertions.assertThrows(BadRequestExceptions.class,() -> superFreteApplicationService.calculateShipping(null));
	}

	@Test
	@DisplayName("Deveria enviar produto e etiquetar")
	@Order(1)
	void deveriaEnviarProdutoEEtiquetar() throws Exception {
		// Arrange
		SuperFreteAddress para = new SuperFreteAddress(
				"jow do parque",
				"Rua da Varzea",
				"",
				"240",
				"jardim prazeres",
				"Jaboatão dos guararapes",
				"SP" ,
				"01140080",
				"jow@teste.com"
		);

		List<ProductInfo> products = List.of(new ProductInfo("Óculos","1","40.50"));
		OrderInfoFromCustomer infoShipping = new OrderInfoFromCustomer(para, products,1);
		ProtocolData shippingOrder = superFreteApplicationService.createShippingOrder(infoShipping);
		OrderService orderAborting = new OrderService(shippingOrder.id(), "não queremos mais");
		abortingRequestDTO = new AbortingRequest(orderAborting);

		// Assert
		Assertions.assertTrue(superFreteApplicationService.payShipping(shippingOrder).success());
	}

	@Test
	@DisplayName("Deveria monitorar produto")
	@Order(2)
	void deveriaMonitorarEnvio() {
		// Opções de status disponíveis na documentação da API do SuperFrete :
		// https://superfrete.readme.io/reference/adicionar-frete-carrinho
		List<String> statusOptions = List.of("canceled", "pending", "released", "posted", "delivered");

		// Assert
		Assertions.assertTrue(statusOptions.contains(superFreteApplicationService.getDeliveryInfo("REVYLcN31yp9u2kPiPlc").status()));
	}

	@Test
	@DisplayName("Deveria cancelar envio do produto")
	@Order(3)
	void deveriaCancelarEnvio() {
		//assert
		Assertions.assertNull(superFreteApplicationService.cancelOrder(abortingRequestDTO).error());
	}

//	@Test
//	@DisplayName("Deveria cancelar envio do produto")
//	@Order(3)
//	void deveriaCancelarEnvio() {
//		// Given
//		RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
//		SuperFreteApplicationService superFreteApplicationServiceLocal = Mockito.mock(SuperFreteApplicationService.class);
//
//		// When
//		superFreteApplicationServiceLocal.cancelOrder(abortingRequestDTO);
//
//		// Then
//		Mockito.verify(restTemplate, Mockito.times(1)).exchange(
//				Mockito.eq(baseURL + "/api/v0/order/cancel"),
//				Mockito.eq(HttpMethod.POST),
//				Mockito.any(),
//				Mockito.eq(String.class)
//		);
//	}
}
