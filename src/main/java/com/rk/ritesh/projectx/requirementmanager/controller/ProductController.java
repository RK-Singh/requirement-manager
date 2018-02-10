package com.rk.ritesh.projectx.requirementmanager.controller;

import com.rk.ritesh.projectx.requirementmanager.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
public class ProductController {
    private List<Product> allProducts = new ArrayList<>();
    private RestTemplate template = new RestTemplate();

    public ProductController() {
        allProducts.add(new Product("1", "Milk", "double toned milk"));
        allProducts.add(new Product("2", "Onion", "Pyazz parantha making"));
        allProducts.add(new Product("3", "Garlic", "chicken cooking"));
        allProducts.add(new Product("4", "Condoms", "to F sagar\'s ass"));
        allProducts.add(new Product("5", "Sanitary Pads", "for Sagar"));
        allProducts.add(new Product("6", "Napkins", ""));
    }

    @GetMapping("/product")
    public ResponseEntity<List<Product>> getAllProduct() {
        return new ResponseEntity<List<Product>>(allProducts, HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") String id) {
        for (Product product : allProducts) {
            if (product.getId().equals(id)) {
                return new ResponseEntity<Product>(product, HttpStatus.OK);
            }
        }
        return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/product")
    public ResponseEntity<Product> createProduct(@RequestBody Product newProduct){
        Integer maxId = 0;
        for(Product product: allProducts){
            if(Integer.valueOf(product.getId()) > maxId){
                maxId = Integer.valueOf(product.getId());
            }
        }
        maxId++;
        newProduct.setId(maxId.toString());
        allProducts.add(newProduct);
        return new ResponseEntity<Product>(HttpStatus.CREATED);
    }

    @PutMapping("/product")
    public ResponseEntity<Product> updateProduct(@RequestBody Product putProduct) {
        for (Product product : allProducts) {
            if (product.getId().equals(putProduct.getId())) {
                product.setName(putProduct.getName());
                product.setDetail(putProduct.getDetail());
                return new ResponseEntity<Product>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") String id){
        Iterator<Product> iterator = allProducts.iterator();
        while(iterator.hasNext()){
            Product product = iterator.next();
            if(product.getId().equals(id)){
                iterator.remove();
                return new ResponseEntity<Product>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
    }
}
