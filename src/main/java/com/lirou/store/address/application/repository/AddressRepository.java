package com.lirou.store.address.application.repository;

import com.lirou.store.address.domain.UserAddress;
import com.lirou.store.user.domain.User;

import java.util.List;

public interface AddressRepository {
    List<UserAddress> getAllUserAddresses(User owner);
    void saveAddress(UserAddress address);
    UserAddress getAddressByIdentifier(String identifier);
}
