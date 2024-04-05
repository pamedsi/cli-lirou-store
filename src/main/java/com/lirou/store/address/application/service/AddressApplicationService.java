package com.lirou.store.address.application.service;

import com.lirou.store.address.application.api.UserAddressDTO;
import com.lirou.store.address.domain.UserAddress;
import com.lirou.store.address.infra.AddressInfraRepository;
import com.lirou.store.user.domain.User;
import com.lirou.store.handler.exceptions.NotFoundException;
import com.lirou.store.user.infra.UserInfraRepository;
import com.lirou.store.security.TokenService;
import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Log4j2
public class AddressApplicationService implements AddressService {
    private final UserInfraRepository userInfraRepository;
    private final AddressInfraRepository addressInfraRepository;
    private final TokenService tokenService;

    public UserAddressDTO getAddressWithIdentifier(String token, String identifier) throws NotFoundException {
        log.info("[starts] AddressApplicationService - getAllAddresses()");
        String email = tokenService.decode(token);
        UserAddress address = addressInfraRepository.getAddressByIdentifier(identifier);
        checkIfUserOwnsTheAddressAndReturnsThem(email, identifier);
        UserAddressDTO addressDTO = new UserAddressDTO(address);
        log.info("[ends] AddressApplicationService - getAllAddresses()");
        return addressDTO;
    }
    public List<UserAddressDTO> getAllAddresses(String token) throws NotFoundException {
        log.info("[starts] AddressApplicationService - getAllAddresses()");
        String email = tokenService.decode(token);
        User user = userInfraRepository.getUserWithEmail(email);
        List<UserAddress> addresses = addressInfraRepository.getAllUserAddresses(user);
        List<UserAddressDTO> addressesDTOs = UserAddressDTO.severalToDTO(addresses);
        log.info("[ends] AddressApplicationService - getAllAddresses()");
        return addressesDTOs;
    }

    public void addNewAddress(String token, UserAddressDTO addressDTO) throws NotFoundException {
        log.info("[starts] AddressApplicationService - addNewAddress()");
        String email = tokenService.decode(token);
        User user = userInfraRepository.getUserWithEmail(email);
        UserAddress newAddress = new UserAddress(addressDTO, user);
        addressInfraRepository.saveAddress(newAddress);
        log.info("[ends] AddressApplicationService - addNewAddress()");
    }
    public void editAddress(String token, UserAddressDTO addressDTO, String identifier) throws NotFoundException {
        log.info("[starts] AddressApplicationService - editAddress()");
        String email = tokenService.decode(token);
        UserAndAddress userAndAddress = checkIfUserOwnsTheAddressAndReturnsThem(email, identifier);
        UserAddress addressToUpdate = userAndAddress.userAddress();
        updateAddressFromDTO(addressToUpdate, addressDTO);
        addressInfraRepository.saveAddress(addressToUpdate);
        log.info("[ends] AddressApplicationService - editAddress()");
    }
    private UserAndAddress checkIfUserOwnsTheAddressAndReturnsThem(String email, String identifier) throws NotFoundException {
        User user = userInfraRepository.getUserWithEmail(email);
        UserAddress address = addressInfraRepository.getAddressByIdentifier(identifier);
        if (!address.getOwner().equals(user)) throw new BadRequestException("Usuário não é dono do endereço que está tentando alterar");
        return new UserAndAddress(user, address);
    }
    private void updateAddressFromDTO(UserAddress addressToBeUpdated, UserAddressDTO addressDTO) {
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
        log.info("[starts] AddressApplicationService - deleteAddress()");
        UserAndAddress userAndAddress = checkIfUserOwnsTheAddressAndReturnsThem(token, addressIdentifier);
        UserAddress addressToDelete = userAndAddress.userAddress();
        addressToDelete.setDeleted(true);
        addressInfraRepository.saveAddress(addressToDelete);
        log.info("[ends] AddressApplicationService - deleteAddress()");
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
