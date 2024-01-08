package com.lirou.store.DTOs;

import java.util.List;

public record ShippingInfoDTO (
        String Platform,
        Address From,
        Address To,
        Number Service,
        List<Product> Products,
        Volumes Volumes
){}

record Address (
        String Name,
        String Address,
        String Complement,
        String Number,
        String District,
        String City,
        String StateAbbr,
        String PostalCode,
        String Email
){}

record Product (
        String Name,
        String Quantity,
        String UnitaryValue
){}

record Volumes (
        Number Height,
        Number Width,
        Number Length,
        Number Weight
){}
