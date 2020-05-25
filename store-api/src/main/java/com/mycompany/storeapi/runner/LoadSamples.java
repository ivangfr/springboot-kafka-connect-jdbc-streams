package com.mycompany.storeapi.runner;

import com.mycompany.storeapi.model.Customer;
import com.mycompany.storeapi.model.Product;
import com.mycompany.storeapi.service.CustomerService;
import com.mycompany.storeapi.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class LoadSamples implements CommandLineRunner {

    @Value("${load-samples.customers.enabled}")
    private boolean loadCustomersEnabled;

    @Value("${load-samples.products.enabled}")
    private boolean loadProductsEnabled;

    private final CustomerService customerService;
    private final ProductService productService;

    public LoadSamples(CustomerService customerService, ProductService productService) {
        this.customerService = customerService;
        this.productService = productService;
    }

    @Override
    public void run(String... args) {

        if (loadCustomersEnabled || loadProductsEnabled) {

            log.info("## Start loading samples of customers and products ...");

            if (loadCustomersEnabled) {
                if (customerService.getAllCustomers().isEmpty()) {
                    customers.forEach(customerRecord -> {
                        String[] customerArr = customerRecord.split(";");
                        Customer customer = new Customer();
                        customer.setName(customerArr[0]);
                        customer.setEmail(customerArr[1]);
                        customer.setAddress(customerArr[2]);
                        customer.setPhone(customerArr[3]);
                        customerService.saveCustomer(customer);
                        log.info("Customer created: {}", customer);
                    });
                } else {
                    log.info("Sample of customers already created");
                }
            }

            if (loadProductsEnabled) {
                if (productService.getAllProducts().isEmpty()) {
                    products.forEach(productsRecord -> {
                        String[] productArr = productsRecord.split(";");
                        Product product = new Product();
                        product.setName(productArr[0]);
                        product.setPrice(new BigDecimal(productArr[1]).toString());
                        productService.saveProduct(product);
                        log.info("Product created: {}", product);
                    });
                } else {
                    log.info("Sample of products already created");
                }
            }
            log.info("## Finished successfully loading samples of customers and products!");
        }
    }

    private static final List<String> customers = Arrays.asList(
            "John Gates;john.gates@test.com;street 1;112233",
            "Mark Bacon;mark.bacon@test.com;street 2;112244",
            "Alex Stone;alex.stone@test.com;street 3;112255",
            "Susan Spice;susan.spice@test.com;street 4;112266",
            "Peter Lopes;peter.lopes@test.com;street 5;112277",
            "Mikael Lopes;mikael.lopes@test.com;street 6;112288",
            "Renato Souza;renato.souza@test.com;street 7;112299",
            "Paul Schneider;paul.schneider@test.com;street 8;113300",
            "Tobias Bohn;tobias.bohn@test.com;street 9;113311",
            "John Star;john.star@test.com;street 10;113322",
            "Rick Sander;rick.sander@test.com;street 11;113333",
            "Nakito Hashi;nakito.hashi@test.com;street 12;113344",
            "Kyo Lo;kyo.lo@test.com;street 13;113355",
            "David Cube;david.cube@test.com;street 14;113366");

    private static final List<String> products = Arrays.asList(
            "iPhone Xr;900", "iPhone Xs;1100", "iPhone X;1000", "iPhone 8;700", "iPhone 7;600", "iPhone SE;500",
            "iPad Pro;800", "iPad Air 2;700", "iPad Mini 4;600",
            "MacBook Pro;2500", "MacBook Air;2000", "Mac Mini;1000", "iMac;1500", "iMac Pro;2000",
            "Apple Watch Series 3;350", "Apple Watch Series 4;400", "Apple TV;350");

}
