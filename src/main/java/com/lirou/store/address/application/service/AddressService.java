package com.lirou.store.address.application.service;

import com.lirou.store.address.application.api.UserAddressDTO;
import com.lirou.store.address.domain.UserAddress;
import com.lirou.store.user.domain.User;
import com.lirou.store.handler.exceptions.NotFoundException;
import com.lirou.store.address.infra.AddressJPARepository;
import com.lirou.store.user.infra.UserRepository;
import com.lirou.store.security.TokenService;
import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final UserRepository userRepository;
    private final AddressJPARepository addressJPARepository;
    private final TokenService tokenService;
    public List<UserAddressDTO> getAllAddresses(String token) throws NotFoundException {
        String email = tokenService.decode(token);

        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("Usuário não encontrado!")
        );
        List<UserAddress> addresses = addressJPARepository.findAllByOwnerAndDeletedFalse(user);
        return UserAddressDTO.severalToDTO(addresses);
    }

    public void addNewAddress(String token, UserAddressDTO addressDTO) throws NotFoundException {
        String email = tokenService.decode(token);
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("Usuário não encontrado!")
        );
        UserAddress newAddress = new UserAddress(addressDTO, user);
        addressJPARepository.save(newAddress);
    }

    public void editAddress(String token, UserAddressDTO addressDTO, String identifier) throws NotFoundException {
        UserAndAddress userAndAddress = checkIfUserOwnsTheAddress(token, identifier);
        UserAddress addressToUpdate = userAndAddress.userAddress();
        updateAddressFromDTO(addressToUpdate, addressDTO);
        addressJPARepository.save(addressToUpdate);
    }

    private UserAndAddress checkIfUserOwnsTheAddress(String token, String identifier) throws NotFoundException {
        String email = tokenService.decode(token);
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("Usuário não encontrado!")
        );
        UserAddress address = addressJPARepository.findByIdentifier(identifier).orElseThrow(
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
        addressJPARepository.save(addressToDelete);
    }

    // Peguei essa regex no Fremework Demoiselle, mexi um pouco no código, mas o original está na URL abaixo:
    //    https://github.com/demoiselle/validation/blob/master/impl/src/main/java/br/gov/frameworkdemoiselle/validation/internal/validator/CepValidator.java

    public static boolean isAValidePostalCode(String cep) {
        if (cep == null || cep.isEmpty()) return false;

        Pattern pattern = Pattern.compile("^(([0-9]{2}\\.[0-9]{3}-[0-9]{3})|([0-9]{2}[0-9]{3}-[0-9]{3})|([0-9]{8}))$");
        Matcher matcher = pattern.matcher(cep);
        return matcher.find();
    }
}

record UserAndAddress(
        User user,
        UserAddress userAddress
){}
