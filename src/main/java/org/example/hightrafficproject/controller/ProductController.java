package org.example.hightrafficproject.controller;

import lombok.RequiredArgsConstructor;
import org.example.hightrafficproject.entity.Product;
import org.example.hightrafficproject.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public List<Product> list() {
        return productService.getAllProducts();
    }
}
