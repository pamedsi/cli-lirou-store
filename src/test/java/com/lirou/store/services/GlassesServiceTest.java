package com.lirou.store.services;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.lirou.store.entities.Glasses;
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
