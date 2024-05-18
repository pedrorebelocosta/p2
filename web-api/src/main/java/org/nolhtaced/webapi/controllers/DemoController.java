package org.nolhtaced.webapi.controllers;

import org.nolhtaced.core.models.Product;
import org.nolhtaced.core.services.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/demo")
public class DemoController {
    @GetMapping("/hello")
    public List<Product> helloWorld() {
        ProductService productService = new ProductService(null);
        return productService.getAll();
    }
}
