package com.lirou.store.services;

import java.math.BigDecimal;
import java.util.UUID;

import com.lirou.store.glasses.application.api.GlassesRequestDTO;
import com.lirou.store.glasses.application.service.GlassesApplicationService;
import com.lirou.store.glasses.domain.Glasses;
import com.lirou.store.glasses.infra.GlassesInfraRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import com.lirou.store.handler.exceptions.NotFoundException;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class GlassesApplicationServiceTest {

	@InjectMocks
	private GlassesApplicationService glassesApplicationService;
	
	@Mock
	private GlassesInfraRepository glassesInfraRepository;
	
	@Mock
	private Glasses glasses;
	
	private static GlassesRequestDTO glassesDTO;
	
	@Captor
	private ArgumentCaptor<Glasses> glassesCaptor;
	
	@BeforeEach
	void beforeEach() {
		glassesDTO = new GlassesRequestDTO("glasses", null, true, 0, null, null, "yellow", null, BigDecimal.valueOf(10.0));
	}

	
	//Testando metodo saveNewGlasses **************************************************
	@Test
	@DisplayName("deveria persistir no banco")
	void deveriaSalvarNoBanco() {
		//arrange

		//act
		glassesApplicationService.saveNewGlasses(glassesDTO);

		//assert
		BDDMockito.then(glassesInfraRepository).should().saveGlasses(glassesCaptor.capture());
		Glasses glasses = glassesCaptor.getValue();

		Assertions.assertEquals(glassesDTO.title(), glasses.getTitle());
		Assertions.assertEquals(glassesDTO.price(), glasses.getPrice());
		Assertions.assertEquals(glassesDTO.available(), glasses.getAvailable());
		Assertions.assertEquals(glassesDTO.color(), glasses.getColor());
	}

	//Testando metodo updateGlasses**************************************************
	@Test
	@DisplayName("deveria conseguir editar o produto")
	void deveriaEditarProduto() {
		// arrange
//		BDDMockito.given(glassesInfraRepository.getGlasses(any())).willReturn(glasses);

		//act
//		glassesApplicationService.editGlasses(any(), glassesDTO);

		//assert
//		BDDMockito.then(glassesInfraRepository).should().saveGlasses(glassesCaptor.capture());
//		Glasses glasses = glassesCaptor.getValue();
//
//		Assertions.assertEquals(glassesDTO.title(), glasses.getTitle());
//		Assertions.assertEquals(glassesDTO.price(), glasses.getPrice());
//		Assertions.assertEquals(glassesDTO.color(), glasses.getColor());
//		Assertions.assertEquals(glassesDTO.brand(), glasses.getBrand());
//		Assertions.assertEquals(glassesDTO.pic(), glasses.getPic());
//		Assertions.assertEquals(glassesDTO.frame(), glasses.getFrame());
//		Assertions.assertEquals(glassesDTO.model(), glasses.getModel());
//
		// Given
		String identifier = UUID.randomUUID().toString();
		BDDMockito.when(glassesInfraRepository.getGlasses(identifier)).thenReturn(glasses);

		// When
		glassesApplicationService.editGlasses(identifier, glassesDTO);

		// Then
		BDDMockito.verify(glassesInfraRepository, Mockito.times(1)).saveGlasses(any());
	}
	
	@Test
	@DisplayName("Não deveria conseguir editar o produto")
	void naoDeveEditarProduto() {
		//arrange
		BDDMockito.given(glassesInfraRepository.getGlasses(any())).willThrow(NotFoundException.class);
//
//		//act
//		//assert
		Assertions.assertThrows(NotFoundException.class,() -> glassesApplicationService.editGlasses(glasses.getIdentifier(), glassesDTO));

//		// THen
		BDDMockito.verify(glassesInfraRepository, BDDMockito.times(0)).saveGlasses(any());
	}
	
	//Testando metodo remove**************************************************
	@Test
	@DisplayName("deveria conseguir remover o produto")
	void deveriaRemoverProduto() {
		// arrange
		BDDMockito.given(glassesInfraRepository.getGlasses(glasses.getIdentifier())).willReturn(glasses);

		// act
		glassesApplicationService.removeGlasses(glasses.getIdentifier());

		// assert
		BDDMockito.verify(glasses, BDDMockito.times(1)).setDeleted(true);
		BDDMockito.then(glassesInfraRepository).should(BDDMockito.times(1)).saveGlasses(glassesCaptor.capture());
//		Glasses capturedGlasses = glassesCaptor.getValue();
//		Assertions.assertEquals(true, capturedGlasses.getDeleted());
	}

	@Test
	@DisplayName("Não deveria conseguir remover o produto, deve lançar exception")
	void naoDeveRemoverProduto() throws NotFoundException {
			//arrange
			BDDMockito.given(glassesInfraRepository.getGlasses(any())).willThrow(NotFoundException.class);
			
			//act
			//assert
			Assertions.assertThrows(NotFoundException.class,() -> glassesApplicationService.editGlasses(glasses.getIdentifier(), glassesDTO));

			// Then
			Mockito.verify(glassesInfraRepository, Mockito.times(0)).saveGlasses(any());
		}
	
	
	//Testando metodo changeAvailability **************************************************
	@Test()
	@DisplayName("deveria retornar que o produto está disponivel")
	void conferirSeEstaDisponivel() throws NotFoundException {
		BDDMockito.given(glassesInfraRepository.getGlasses("123")).willReturn(glasses);
		
		// Assert && Act
		Assertions.assertEquals("disponibilizado!", glassesApplicationService.changeAvailability("123", true));
	}
	
	@Test()
	@DisplayName("deveria retornar que o produto está indisponível")
	void conferirSeFoiIndisponibilizado()  {
		BDDMockito.given(glassesInfraRepository.getGlasses("123")).willReturn((glasses));
		
		//Assert && Act
		Assertions.assertEquals("indisponibilizado!", glassesApplicationService.changeAvailability("123", false));
	}
	
	@Test()
	@DisplayName("deveria retornar que o produto está indisponível e estourar uma exception")
	void conferirSeEstaRetornaNotfoundException() {
		BDDMockito.given(glassesInfraRepository.getGlasses(any())).willThrow(NotFoundException.class);
		
		// Assert && Act
		Assertions.assertThrows(NotFoundException.class,() -> glassesApplicationService.changeAvailability("123", true));
	}
}
