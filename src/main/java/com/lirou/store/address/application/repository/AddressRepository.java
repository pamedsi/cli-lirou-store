package com.lirou.store.address.application.repository;

import com.lirou.store.address.domain.UserAddress;
import com.lirou.store.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface AddressRepository {
    List<UserAddress> getAllUserAdresses(User owner);
    Optional<UserAddress> getUserByIdentifier(String identifier);
}
