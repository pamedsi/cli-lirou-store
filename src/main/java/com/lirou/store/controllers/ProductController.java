package com.lirou.store.controllers;

import com.lirou.store.models.BaseController;
import com.lirou.store.services.ProductService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController extends BaseController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
}
