package com.lirou.store.services;

import java.math.BigDecimal;
import java.util.Optional;

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

import com.lirou.store.domain.DTOs.GlassesDTO;
import com.lirou.store.domain.entities.Glasses;
import com.lirou.store.exceptions.NameExisteInDatabaseException;
import com.lirou.store.exceptions.NotFoundException;
import com.lirou.store.repository.GlassesRepository;

@ExtendWith(MockitoExtension.class)
public class GlassesServiceTest {

	@InjectMocks
	private GlassesService glassesService;
	
	@Mock
	private GlassesRepository glassesRepository;
	
	@Mock
	private Glasses glasses;
	
	private static GlassesDTO glassesdto;
	
	@Captor
	private ArgumentCaptor<Glasses> glassesCaptor;
	
	@BeforeEach
	void beforeach() {
		glassesdto = new GlassesDTO(null, "glasses", null, true, 0, null, null, "yellow", null, BigDecimal.valueOf(10.0));

	}

	
	//Testando metodo saveNewGlasses **************************************************
	@Test
	@DisplayName("deveria persistir no banco")
	void deveriaSalvarNoBanco() throws NotFoundException, NameExisteInDatabaseException {
		//arrange
		BDDMockito.given(glassesRepository.existsByTitleAndDeletedFalse(glassesdto.title())).willReturn(false);
		
		//act
		glassesService.saveNewGlasses(glassesdto);
		
		//assert
		BDDMockito.then(glassesRepository).should().save(glassesCaptor.capture());
		Glasses glasses = glassesCaptor.getValue();
		
		Assertions.assertEquals(glassesdto.title(), glasses.getTitle());
		Assertions.assertEquals(glassesdto.price(), glasses.getPrice());
		Assertions.assertEquals(glassesdto.available(), glasses.getAvailable());
		Assertions.assertEquals(glassesdto.color(), glasses.getColor());
	}
	
	@Test
	@DisplayName("deveria dar error persistir no banco  e lançar exception")
	void deveriaDarErrorAoSalvarNoBanco() {
		//arrange
		BDDMockito.given(glassesRepository.existsByTitleAndDeletedFalse(glassesdto.title())).willReturn(true);
		
		//assert && act
		Assertions.assertThrows(NameExisteInDatabaseException.class,() -> glassesService.saveNewGlasses(glassesdto));

	}
	
	//Testando metodo updateGlasses**************************************************
	@Test
	@DisplayName("deveria conseguir editar o produto")
	void editarProduto() throws NotFoundException {
		//arrange
		BDDMockito.given(glassesRepository.findByIdentifierAndDeletedFalse(glassesdto.identifier())).willReturn(Optional.of(new Glasses()));
		
		//act
		glassesService.updateGlasses(glassesdto.identifier(),glassesdto);
				
		//assert
		BDDMockito.then(glassesRepository).should().save(glassesCaptor.capture());
		Glasses glasses = glassesCaptor.getValue();
		
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
		BDDMockito.given(glassesRepository.findByIdentifierAndDeletedFalse(glassesdto.identifier())).willReturn(Optional.empty());
		
		//act		
		//assert
		Assertions.assertThrows(NotFoundException.class,() -> glassesService.updateGlasses(glassesdto.identifier(),glassesdto));

	}
	
	//Testando metodo remove**************************************************
		@Test
		@DisplayName("deveria conseguir remover o produto")
		void removerProduto() throws NotFoundException {
			//arrange
			BDDMockito.given(glassesRepository.findByIdentifierAndDeletedFalse(glassesdto.identifier())).willReturn(Optional.of(new Glasses()));
			
			//act
			glassesService.removeGlasses(glassesdto.identifier());
					
			//assert
			BDDMockito.then(glassesRepository).should().save(glassesCaptor.capture());
			Glasses glasses = glassesCaptor.getValue();
			
			Assertions.assertEquals(true,glasses.getDeleted());
		}
		
		@Test
		@DisplayName("Não deveria conseguir remover o produto, deve lançar exception")
		void nãoDeveRemoverProduto() throws NotFoundException {
			//arrange
			BDDMockito.given(glassesRepository.findByIdentifierAndDeletedFalse(glassesdto.identifier())).willReturn(Optional.empty());
			
			//act		
			//assert
			Assertions.assertThrows(NotFoundException.class,() -> glassesService.updateGlasses(glassesdto.identifier(),glassesdto));

		}
	
	
	//Testando metodo changeAvailability **************************************************
	@Test()
	@DisplayName("deveria retornar que o produto está disponivel")
	void conferirSeEstaDisponivel() throws NotFoundException {
		BDDMockito.given(glassesRepository.findByIdentifierAndDeletedFalse("123")).willReturn(Optional.of(glasses));
		
		//Assert && Act
		Assertions.assertEquals("disponibilizado!", glassesService.changeAvailability("123", true));
	}
	
	@Test()
	@DisplayName("deveria retornar que o produto está indisponivel")
	void conferirSeEstaIndisponivel() throws NotFoundException {
		BDDMockito.given(glassesRepository.findByIdentifierAndDeletedFalse("123")).willReturn(Optional.of(glasses));
		
		//Assert && Act
		Assertions.assertEquals("indisponibilizado!", glassesService.changeAvailability("123", false));
	}
	
	@Test()
	@DisplayName("deveria retornar que o produto está indisponivel e estourar uma exception")
	void conferirSeEstaRetornaNotfoundException() {
		BDDMockito.given(glassesRepository.findByIdentifierAndDeletedFalse("123")).willReturn(Optional.empty());
		
		//Assert && Act
		Assertions.assertThrows(NotFoundException.class,() -> glassesService.changeAvailability("123", true));
	}
}
