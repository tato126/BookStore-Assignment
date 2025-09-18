package org.bookstore.bookstore.products.initailizer;

import lombok.RequiredArgsConstructor;
import org.bookstore.bookstore.products.Products;
import org.bookstore.bookstore.products.repository.ProductsRepository;
import org.bookstore.bookstore.products.service.ProductsService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ProductInitializer implements CommandLineRunner {

    private final ProductsRepository productsRepository;
    private final ProductsService productsService;

    @Override
    public void run(String... args) throws Exception {

        for (int i = 0; i < 100; i++) {
            Products product = new Products();
            product.setProductName("Product " + i);
            product.setStockQuantity(Long.valueOf(i));
            product.setDescription("Description " + i);
            product.setPrice(Long.valueOf(i));
            productsRepository.save(product);
        }
    }
}
