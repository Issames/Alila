package com.gestionproduit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gestionproduit.entities.Product;
import com.gestionproduit.repositories.ProductRepository;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    
    @GetMapping("/add")
    public String showAddProductForm(Model model) {
    model.addAttribute("product", new Product());
    return "add-product";
}

    @PostMapping("/add")
    public String addProduct(@ModelAttribute Product product, Model model) {
        productRepository.save(product);
        return "product-confirmation";
    }

    
   
    @PostMapping("/update") 
    public String updateProduct(@ModelAttribute Product product){

        Product existingProduct = productRepository.findById(product.getId()).orElse(null);
        if(existingProduct != null){
            existingProduct.setName(product.getName());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setQuantity(product.getQuantity());
            productRepository.save(existingProduct);
        }
        return "product-confirmation";
    }
    @GetMapping("/update/{id}")
    public String showUpdateProductForm(@PathVariable Long id, Model model) {
    Product product = productRepository.findById(id).orElse(null);
    if (product != null) {
        model.addAttribute("product", product);
        return "update-product";
    } else {
        model.addAttribute("message", "Product not found!");
        return "product-confirmation";
    }
    }
    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id, Model model) {
    Product existingProduct = productRepository.findById(id).orElse(null);
    
    if (existingProduct != null) {
        productRepository.delete(existingProduct);
        model.addAttribute("message", "Product deleted successfully!");
    } else {
        model.addAttribute("message", "Product not found!");
    }
    return "product-confirmation";
    }



}
