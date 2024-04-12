package com.lirou.store.address.application.service;

import com.lirou.store.address.domain.UserAddress;
import com.lirou.store.user.domain.User;

public record UserAndAddress(
        User user,
        UserAddress userAddress
){}