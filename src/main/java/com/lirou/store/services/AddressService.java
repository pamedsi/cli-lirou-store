package com.lirou.store.services;

import com.lirou.store.domain.DTOs.UserAddressDTO;
import com.lirou.store.domain.entities.AddressEntity;
import com.lirou.store.domain.entities.User;
import com.lirou.store.exceptions.NotFoundException;
import com.lirou.store.repository.AddressRepository;
import com.lirou.store.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    public List<UserAddressDTO> getAllAddresses(String email) throws NotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("Usuário não encontrado!")
        );
        List<AddressEntity> addresses = addressRepository.findAllByOwner(user);

        return UserAddressDTO.severalToDTO(addresses);
    }
}
