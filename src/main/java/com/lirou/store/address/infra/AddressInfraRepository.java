package com.lirou.store.address.infra;

import com.lirou.store.address.application.repository.AddressRepository;
import com.lirou.store.address.domain.UserAddress;
import com.lirou.store.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Log4j2
public class AddressInfraRepository  implements AddressRepository {
    private final AddressJPARepository addressJPARepository;

    @Override
    public List<UserAddress> getAllUserAdresses(User owner) {
        log.info("[starts] AddressInfraRepository - getAllAddressesOfUser");
        List<UserAddress> userAddresses = addressJPARepository.findAllByOwnerAndDeletedFalse(owner);
        log.info("[ends] AddressInfraRepository - getAllAddressesOfUser");
        return userAddresses;
    }

    @Override
    public Optional<UserAddress> getUserByIdentifier(String identifier) {
        log.info("[starts] AddressInfraRepository - getAllAddressesOfUser");
        Optional<UserAddress> userAddress = addressJPARepository.findByIdentifier(identifier);
        log.info("[ends] AddressInfraRepository - getAllAddressesOfUser");
        return userAddress;
    }
}
