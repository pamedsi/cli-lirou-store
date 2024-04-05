package com.lirou.store.services;

import java.math.BigDecimal;
import java.util.Optional;

import com.lirou.store.glasses.application.api.GlassesDTO;
import com.lirou.store.glasses.application.service.GlassesApplicationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.lirou.store.handler.exceptions.NameExisteInDatabaseException;
import com.lirou.store.handler.exceptions.NotFoundException;
import com.lirou.store.glasses.infra.GlassesJPARepository;

@ExtendWith(MockitoExtension.class)
public class GlassesDTOServiceTest {

	@InjectMocks
	private GlassesApplicationService glassesApplicationService;
	
	@Mock
	private GlassesJPARepository glassesJPARepository;
	
	@Mock
	private com.lirou.store.glasses.domain.Glasses glasses;
	
	private static GlassesDTO glassesdto;
	
	@Captor
	private ArgumentCaptor<com.lirou.store.glasses.domain.Glasses> glassesCaptor;
	
	@BeforeEach
	void beforeach() {
		glassesdto = new GlassesDTO(null, "glasses", null, true, 0, null, null, "yellow", null, BigDecimal.valueOf(10.0));

	}

	
	//Testando metodo saveNewGlasses **************************************************
	@Test
	@DisplayName("deveria persistir no banco")
	void deveriaSalvarNoBanco() throws NotFoundException, NameExisteInDatabaseException {
		//arrange
		BDDMockito.given(glassesJPARepository.existsByTitleAndDeletedFalse(glassesdto.title())).willReturn(false);
		
		//act
		glassesApplicationService.saveNewGlasses(glassesdto);
		
		//assert
		BDDMockito.then(glassesJPARepository).should().save(glassesCaptor.capture());
		com.lirou.store.glasses.domain.Glasses glasses = glassesCaptor.getValue();
		
		Assertions.assertEquals(glassesdto.title(), glasses.getTitle());
		Assertions.assertEquals(glassesdto.price(), glasses.getPrice());
		Assertions.assertEquals(glassesdto.available(), glasses.getAvailable());
		Assertions.assertEquals(glassesdto.color(), glasses.getColor());
	}
	
	@Test
	@DisplayName("deveria dar error persistir no banco  e lançar exception")
	void deveriaDarErrorAoSalvarNoBanco() {
		//arrange
		BDDMockito.given(glassesJPARepository.existsByTitleAndDeletedFalse(glassesdto.title())).willReturn(true);
		
		//assert && act
		Assertions.assertThrows(NameExisteInDatabaseException.class,() -> glassesApplicationService.saveNewGlasses(glassesdto));

	}
	
	//Testando metodo updateGlasses**************************************************
	@Test
	@DisplayName("deveria conseguir editar o produto")
	void editarProduto() throws NotFoundException {
		//arrange
		BDDMockito.given(glassesJPARepository.findByIdentifierAndDeletedFalse(glassesdto.identifier())).willReturn(Optional.of(new com.lirou.store.glasses.domain.Glasses()));
		
		//act
		glassesApplicationService.editGlasses(glassesdto.identifier(),glassesdto);
				
		//assert
		BDDMockito.then(glassesJPARepository).should().save(glassesCaptor.capture());
		com.lirou.store.glasses.domain.Glasses glasses = glassesCaptor.getValue();
		
		Assertions.assertEquals(glassesdto.title(), glasses.getTitle());
		Assertions.assertEquals(glassesdto.price(), glasses.getPrice());
		Assertions.assertEquals(glassesdto.color(), glasses.getColor());
		Assertions.assertEquals(glassesdto.brand(), glasses.getBrand());
		Assertions.assertEquals(glassesdto.pic(), glasses.getPic());
		Assertions.assertEquals(glassesdto.frame(), glasses.getFrame());
		Assertions.assertEquals(glassesdto.model(), glasses.getModel());
		
	}
	
	@Test
	@DisplayName("Não deveria conseguir editar o produto")
	void nãoDeveEditarProduto() throws NotFoundException {
		//arrange
		BDDMockito.given(glassesJPARepository.findByIdentifierAndDeletedFalse(glassesdto.identifier())).willReturn(Optional.empty());
		
		//act		
		//assert
		Assertions.assertThrows(NotFoundException.class,() -> glassesApplicationService.editGlasses(glassesdto.identifier(),glassesdto));

	}
	
	//Testando metodo remove**************************************************
		@Test
		@DisplayName("deveria conseguir remover o produto")
		void removerProduto() throws NotFoundException {
			//arrange
			BDDMockito.given(glassesJPARepository.findByIdentifierAndDeletedFalse(glassesdto.identifier())).willReturn(Optional.of(new com.lirou.store.glasses.domain.Glasses()));
			
			//act
			glassesApplicationService.removeGlasses(glassesdto.identifier());
					
			//assert
			BDDMockito.then(glassesJPARepository).should().save(glassesCaptor.capture());
			com.lirou.store.glasses.domain.Glasses glasses = glassesCaptor.getValue();
			
			Assertions.assertEquals(true,glasses.getDeleted());
		}
		
		@Test
		@DisplayName("Não deveria conseguir remover o produto, deve lançar exception")
		void nãoDeveRemoverProduto() throws NotFoundException {
			//arrange
			BDDMockito.given(glassesJPARepository.findByIdentifierAndDeletedFalse(glassesdto.identifier())).willReturn(Optional.empty());
			
			//act		
			//assert
			Assertions.assertThrows(NotFoundException.class,() -> glassesApplicationService.editGlasses(glassesdto.identifier(),glassesdto));

		}
	
	
	//Testando metodo changeAvailability **************************************************
	@Test()
	@DisplayName("deveria retornar que o produto está disponivel")
	void conferirSeEstaDisponivel() throws NotFoundException {
		BDDMockito.given(glassesJPARepository.findByIdentifierAndDeletedFalse("123")).willReturn(Optional.of(glasses));
		
		//Assert && Act
		Assertions.assertEquals("disponibilizado!", glassesApplicationService.changeAvailability("123", true));
	}
	
	@Test()
	@DisplayName("deveria retornar que o produto está indisponivel")
	void conferirSeEstaIndisponivel() throws NotFoundException {
		BDDMockito.given(glassesJPARepository.findByIdentifierAndDeletedFalse("123")).willReturn(Optional.of(glasses));
		
		//Assert && Act
		Assertions.assertEquals("indisponibilizado!", glassesApplicationService.changeAvailability("123", false));
	}
	
	@Test()
	@DisplayName("deveria retornar que o produto está indisponivel e estourar uma exception")
	void conferirSeEstaRetornaNotfoundException() {
		BDDMockito.given(glassesJPARepository.findByIdentifierAndDeletedFalse("123")).willReturn(Optional.empty());
		
		//Assert && Act
		Assertions.assertThrows(NotFoundException.class,() -> glassesApplicationService.changeAvailability("123", true));
	}
}
