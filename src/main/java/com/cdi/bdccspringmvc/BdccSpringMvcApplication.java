package com.cdi.bdccspringmvc;

import com.cdi.bdccspringmvc.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.cdi.bdccspringmvc.entities.Product;
import org.springframework.boot.security.autoconfigure.SecurityAutoConfiguration;
import org.springframework.boot.security.autoconfigure.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.security.autoconfigure.actuate.web.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication /*(exclude = {SecurityAutoConfiguration.class,
        UserDetailsServiceAutoConfiguration.class,
        ManagementWebSecurityAutoConfiguration.class})*/
public class BdccSpringMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(BdccSpringMvcApplication.class, args);
    }
    @Bean
    public CommandLineRunner initializer(ProductRepository productRepository){

        return args -> {
            productRepository.save(Product.builder()
                            .name("Computer")
                            .price((5400))
                            .quantity(12)
                        .build());
            productRepository.save(Product.builder()
                    .name("printer")
                    .price((1200))
                    .quantity(14)
                    .build());
            productRepository.save(Product.builder()
                    .name("Smart Phone")
                    .price((210))
                    .quantity(100)
                    .build());

            productRepository.findAll().forEach(
                    p-> System.out.print("produit ="+p.toString())
            );
        };
    }

}
