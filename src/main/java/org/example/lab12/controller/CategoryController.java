package org.example.lab12.controller;

import org.example.lab12.model.Category;
import org.example.lab12.model.Product;
import org.example.lab12.repository.CategoryRepository;
import org.example.lab12.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @PostMapping
    public Category createCategory(@RequestBody Category category) {
        return categoryRepository.save(category);
    }

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @PostMapping("/{categoryId}/products")
    public Category addProductsToCategory(@PathVariable Long categoryId, @RequestBody List<Long> productIds) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        List<Product> products = productRepository.findAllById(productIds);
        products.forEach(product -> product.setCategory(category));

        productRepository.saveAll(products);
        return categoryRepository.findById(categoryId).get(); // Return updated category
    }
}
