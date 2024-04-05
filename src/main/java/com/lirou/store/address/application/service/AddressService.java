package com.lirou.store.address.application.service;

import com.lirou.store.address.application.api.UserAddressDTO;
import com.lirou.store.address.domain.UserAddress;
import com.lirou.store.user.domain.User;
import com.lirou.store.exceptions.NotFoundException;
import com.lirou.store.address.infra.AddressRepository;
import com.lirou.store.user.infra.UserRepository;
import com.lirou.store.security.TokenService;
import jakarta.ws.rs.BadRequestException;
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
        List<UserAddress> addresses = addressRepository.findAllByOwnerAndDeletedFalse(user);
        return UserAddressDTO.severalToDTO(addresses);
    }

    public void addNewAddress(String token, UserAddressDTO addressDTO) throws NotFoundException {
        String email = tokenService.decode(token);
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("Usuário não encontrado!")
        );
        UserAddress newAddress = new UserAddress(addressDTO, user);
        addressRepository.save(newAddress);
    }

    public void editAddress(String token, UserAddressDTO addressDTO, String identifier) throws NotFoundException {
        UserAndAddress userAndAddress = checkIfUserOwnsTheAddress(token, identifier);
        UserAddress addressToUpdate = userAndAddress.userAddress();
        updateAddressFromDTO(addressToUpdate, addressDTO);
        addressRepository.save(addressToUpdate);
    }

    private UserAndAddress checkIfUserOwnsTheAddress(String token, String identifier) throws NotFoundException {
        String email = tokenService.decode(token);
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("Usuário não encontrado!")
        );
        UserAddress address = addressRepository.findByIdentifier(identifier).orElseThrow(
                () -> new NotFoundException("Endereço não encontrado!")
        );
        if (!address.getOwner().equals(user)) throw new BadRequestException("Usuário não é dono do endereço que está tentando alterar");
        return new UserAndAddress(user, address);
    }

    private void updateAddressFromDTO(UserAddress addressToBeUpdated, UserAddressDTO addressDTO){
        addressToBeUpdated.setCity(addressDTO.city());
        addressToBeUpdated.setObs(addressDTO.obs());
        addressToBeUpdated.setComplement(addressDTO.complement());
        addressToBeUpdated.setNumber(addressDTO.number());
        addressToBeUpdated.setState(addressDTO.state());
        addressToBeUpdated.setDistrict(addressDTO.district());
        addressToBeUpdated.setStreet(addressDTO.street());
        addressToBeUpdated.setPostalCode(addressDTO.postalCode());
    }

    public void deleteAddress(String token, String addressIdentifier) throws NotFoundException {
        UserAndAddress userAndAddress = checkIfUserOwnsTheAddress(token, addressIdentifier);
        UserAddress addressToDelete = userAndAddress.userAddress();
        addressToDelete.setDeleted(true);
        addressRepository.save(addressToDelete);
    }
}

record UserAndAddress(
        User user,
        UserAddress userAddress
){}
