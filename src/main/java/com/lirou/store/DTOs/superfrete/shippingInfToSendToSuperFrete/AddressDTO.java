package com.lirou.store.DTOs.superfrete.shippingInfToSendToSuperFrete;

public record AddressDTO (
            String name,
            String address,
            String complement,
            String number,
            String district,
            String document,
            String city,
            String state_abbr,
            String postal_code,
            String location_number,
            String country_id,
            String email
){}
