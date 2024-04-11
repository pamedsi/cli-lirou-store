package com.lirou.store.services;

import java.math.BigDecimal;

import com.lirou.store.glasses.application.api.GlassesRequestDTO;
import com.lirou.store.glasses.application.repository.GlassesRepository;
import com.lirou.store.glasses.application.service.GlassesApplicationService;
import com.lirou.store.glasses.domain.Glasses;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import com.lirou.store.handler.exceptions.NotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class GlassesApplicationServiceTest {

	@InjectMocks
	private GlassesApplicationService glassesApplicationService;
	
	@Mock
	private GlassesRepository glassesRepository;
	
	@Mock
	private Glasses glasses;
	
	private static GlassesRequestDTO glassesDTO;
	
	@Captor
	private ArgumentCaptor<Glasses> glassesCaptor;
	
	@BeforeEach
	void beforeEach() {
		glassesDTO = new GlassesRequestDTO("glasses", null, true, 0, null, null, "yellow", null, BigDecimal.valueOf(10.0));
	}
	
	// Testando método saveNewGlasses **************************************************
	@Test
	@DisplayName("Deveria persistir no banco")
	void deveriaSalvarNoBanco() {
		//arrange

		//act
		glassesApplicationService.saveNewGlasses(glassesDTO);

		//assert
		BDDMockito.then(glassesRepository).should().saveGlasses(glassesCaptor.capture());
		Glasses glasses = glassesCaptor.getValue();

		assertEquals(glassesDTO.title(), glasses.getTitle());
		assertEquals(glassesDTO.price(), glasses.getPrice());
		assertEquals(glassesDTO.available(), glasses.getAvailable());
		assertEquals(glassesDTO.color(), glasses.getColor());
	}

	@Test
	@DisplayName("Deveria conseguir editar o produto")
	void deveriaEditarProduto() {
		// Given
		Glasses glasses1 = new Glasses(glassesDTO);
		BDDMockito.when(glassesRepository.getGlasses(any())).thenReturn(glasses1);
		glassesApplicationService.editGlasses(glasses1.getIdentifier(), glassesDTO);

		// When
		BDDMockito.then(glassesRepository).should().saveGlasses(glassesCaptor.capture());

		// Then
		Glasses savedGlasses = glassesCaptor.getValue();
		Assertions.assertEquals(glassesDTO.title(), savedGlasses.getTitle());
		Assertions.assertEquals(glassesDTO.price(), savedGlasses.getPrice());
		Assertions.assertEquals(glassesDTO.color(), savedGlasses.getColor());
		Assertions.assertEquals(glassesDTO.brand(), savedGlasses.getBrand());
		Assertions.assertEquals(glassesDTO.pic(), savedGlasses.getPic());
		Assertions.assertEquals(glassesDTO.frame(), savedGlasses.getFrame());
		Assertions.assertEquals(glassesDTO.model(), savedGlasses.getModel());
		BDDMockito.verify(glassesRepository, Mockito.times(1)).saveGlasses(any());
	}
	
	@Test
	@DisplayName("Não deveria conseguir editar o produto")
	void naoDeveEditarProduto() {
		// Arrange
		BDDMockito.given(glassesRepository.getGlasses(any())).willThrow(NotFoundException.class);

		// Assert
		Assertions.assertThrows(NotFoundException.class,() -> glassesApplicationService.editGlasses(glasses.getIdentifier(), glassesDTO));

		// Then
		BDDMockito.verify(glassesRepository, BDDMockito.times(0)).saveGlasses(any());
	}
	
	//Testando método remove**************************************************
	@Test
	@DisplayName("Deveria conseguir remover o produto")
	void deveriaRemoverProduto() {
		Glasses glasses1 = new Glasses(glassesDTO);
		// Arrange
		BDDMockito.given(glassesRepository.getGlasses(any())).willReturn(glasses1);

		// Act
		glassesApplicationService.removeGlasses(any());

		// Assert
		BDDMockito.then(glassesRepository).should(BDDMockito.times(1)).saveGlasses(glassesCaptor.capture());

		Glasses capturedGlasses = glassesCaptor.getValue();
		Assertions.assertTrue(capturedGlasses.getDeleted());
	}

	@Test
	@DisplayName("Não deveria conseguir remover o produto, deve lançar exception")
	void naoDeveRemoverProduto() throws NotFoundException {
			// Arrange
			BDDMockito.given(glassesRepository.getGlasses(any())).willThrow(NotFoundException.class);

			// Assert
			Assertions.assertThrows(NotFoundException.class,() -> glassesApplicationService.editGlasses(glasses.getIdentifier(), glassesDTO));

			// Then
			Mockito.verify(glassesRepository, Mockito.times(0)).saveGlasses(any());
		}
	
	
	//Testando método changeAvailability **************************************************
	@Test()
	@DisplayName("Deveria retornar que o produto está disponivel")
	void conferirSeEstaDisponivel() throws NotFoundException {
		BDDMockito.given(glassesRepository.getGlasses("123")).willReturn(glasses);
		
		// Assert && Act
		assertEquals("disponibilizado!", glassesApplicationService.changeAvailability("123", true));
	}
	
	@Test()
	@DisplayName("Deveria retornar que o produto está indisponível")
	void conferirSeFoiIndisponibilizado()  {
		BDDMockito.given(glassesRepository.getGlasses("123")).willReturn((glasses));
		
		//Assert && Act
		assertEquals("indisponibilizado!", glassesApplicationService.changeAvailability("123", false));
	}
	
	@Test()
	@DisplayName("Deveria retornar que o produto está indisponível e estourar uma exception")
	void conferirSeEstaRetornaNotfoundException() {
		BDDMockito.given(glassesRepository.getGlasses(any())).willThrow(NotFoundException.class);
		
		// Assert && Act
		Assertions.assertThrows(NotFoundException.class,() -> glassesApplicationService.changeAvailability("123", true));
	}
}
