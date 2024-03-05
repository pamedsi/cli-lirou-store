package com.lirou.store.services;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.lirou.store.exceptions.BadRequestException;
import com.lirou.store.models.superfrete.AbortingRequest;
import com.lirou.store.models.superfrete.OrderInfoFromCustomer;
import com.lirou.store.models.superfrete.OrderService;
import com.lirou.store.models.superfrete.ProtocolData;
import com.lirou.store.models.superfrete.shippingInfToSendToSuperFrete.ProductInfo;
import com.lirou.store.models.superfrete.shippingInfToSendToSuperFrete.SuperFreteAddress;



@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SuperFreteServiceTest {

	@Autowired
	private SuperFreteService superFreteService;
	
	@Value("${CEP}")
	private String from;
	
	private static AbortingRequest abortingRequestDTO;
	
	//**************metodo calcular frete
	@Test
	@DisplayName("deveria calcular frete")
	void deveriaCalcularFrete() throws Exception {
		
		//arrange
		//ReflectionTestUtils.setField(superFreteService, "from", from);
		
		//assert
		Assertions.assertEquals(2, superFreteService.calculateShipping("01140080").size());
	}
	
	@Test
	@DisplayName("deveria estourar exception")
	void naoDeveriaCalcularFrete() throws Exception {
		//arange
		String cep = null;
		
		//assert
		Assertions.assertThrows(BadRequestException.class,() -> superFreteService.calculateShipping(cep));
	}
	
	@Test
	@DisplayName("deveria enviar produto e entiquetar")
	@Order(1)
	void deveriaEnviarProdutoEentiquetar() throws Exception {
		//arange
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
		List<ProductInfo> products = new ArrayList<>();
		ProductInfo productInfo = new ProductInfo("oculos","1","40.50");
		products.add(productInfo);
		OrderInfoFromCustomer infoShipping = new OrderInfoFromCustomer(para,products,1);
		ProtocolData shippingOrder = superFreteService.createShippingOrder(infoShipping);

		List<String> orders = new ArrayList<>();
		orders.add(shippingOrder.id());
		OrderService orderAborting = new OrderService(orders.get(0), "não queremos mais");
		abortingRequestDTO = new AbortingRequest(orderAborting);

		//assert
		Assertions.assertEquals(true, superFreteService.payShipping(shippingOrder).success());
	}
	
	@Test
	@DisplayName("deveria monitorar produto")
	@Order(2)
	void deveriaMonitorarEnvio() throws Exception {
		
		//assert
		Assertions.assertEquals("released", superFreteService.getDeliveryInfo("REVYLcN31yp9u2kPiPlc").status());
	}
	
	@Test
	@DisplayName("deveria cancelar envio do produto")
	@Order(3)
	void deveriaCancelarEnvio() throws Exception {
		//assert
		Assertions.assertEquals(200, 
				superFreteService
				.cancelOrder(abortingRequestDTO));
	}
	
	
	
}
