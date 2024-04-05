package com.lirou.store.address.infra;

import com.lirou.store.address.application.repository.AddressRepository;
import com.lirou.store.address.domain.UserAddress;
import com.lirou.store.handler.exceptions.NotFoundException;
import com.lirou.store.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Log4j2
@Repository
public class AddressInfraRepository implements AddressRepository {
    private final AddressJPARepository addressJPARepository;

    @Override
    public List<UserAddress> getAllUserAddresses(User owner) {
        log.info("[starts] AddressInfraRepository - getAllAddressesOfUser()");
        List<UserAddress> userAddresses = addressJPARepository.findAllByOwnerAndDeletedFalse(owner);
        log.info("[ends] AddressInfraRepository - getAllAddressesOfUser()");
        return userAddresses;
    }
    @Override
    public void saveAddress(UserAddress address) {
        log.info("[starts] AddressInfraRepository - saveNewAddress()");
        addressJPARepository.save(address);
        log.info("[ends] AddressInfraRepository - saveNewAddress()");
    }
    @Override
    public UserAddress getAddressByIdentifier(String identifier) {
        log.info("[starts] AddressInfraRepository - getAddressByIdentifier()");
        UserAddress address = addressJPARepository.findByIdentifierAndDeletedFalse(identifier).orElseThrow(
                () -> new NotFoundException("Endereço não encontrado!")
        );
        log.info("[ends] AddressInfraRepository - getAddressByIdentifier()");
        return address;
    }
}
