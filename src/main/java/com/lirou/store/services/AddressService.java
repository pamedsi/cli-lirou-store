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

    public void editAddress(String token, UserAddressDTO addressDTO, String identifier) throws NotFoundException {
        String email = tokenService.decode(token);
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("Usuário não encontrado!")
        );
        AddressEntity updatedAddress = addressRepository.findByIdentifier(identifier).orElseThrow(
                () -> new NotFoundException("Endereço não encontrado!")
        );;

        updatedAddress.setCity(addressDTO.city());
        updatedAddress.setObs(addressDTO.obs());
        updatedAddress.setComplement(addressDTO.complement());
        updatedAddress.setNumber(addressDTO.number());
        updatedAddress.setState(addressDTO.state());
        updatedAddress.setNeighborhood(addressDTO.neighborhood());
        updatedAddress.setStreet(addressDTO.street());
        updatedAddress.setPostalCode(addressDTO.postalCode());

        addressRepository.save(updatedAddress);

    }

    public void deleteAddress(String token, String addressIdentifier) throws NotFoundException {
        String email = tokenService.decode(token);
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("Usuário não encontrado!")
        );
        AddressEntity addressToDelete = addressRepository.findByIdentifier(addressIdentifier).orElseThrow(
                () -> new NotFoundException("Endereço não encontrado!")
        );;

        addressToDelete.setDeleted(true);
        addressRepository.save(addressToDelete);

    }
}
