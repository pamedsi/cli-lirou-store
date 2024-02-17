package com.lirou.store.services;

import com.lirou.store.domain.DTOs.UserAddressDTO;
import com.lirou.store.domain.entities.AddressEntity;
import com.lirou.store.domain.entities.User;
import com.lirou.store.exceptions.NotFoundException;
import com.lirou.store.repository.AddressRepository;
import com.lirou.store.repository.UserRepository;
import com.lirou.store.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final TokenService tokenService;
    public List<UserAddressDTO> getAllAddresses(String token) throws NotFoundException {
        String email = tokenService.decode(token);

        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("Usuário não encontrado!")
        );
        List<AddressEntity> addresses = addressRepository.findAllByOwner(user);
        return UserAddressDTO.severalToDTO(addresses);
    }

    public void addNewAddress(String token, UserAddressDTO addressDTO) throws NotFoundException {
        String email = tokenService.decode(token);
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("Usuário não encontrado!")
        );
        AddressEntity newAddress = new AddressEntity(addressDTO, user);
        addressRepository.save(newAddress);
    }
}
