package com.cdi.bdccspringmvc.web;

import com.cdi.bdccspringmvc.entities.Product;
import com.cdi.bdccspringmvc.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/user/index")
    @PreAuthorize("hasRole('USER')")
    public String index(@RequestParam(defaultValue = "") String keyword, Model model) {
        List<Product> productList;
        productList = keyword.length() > 0 ?
                productRepository.findByNameContainsIgnoreCase(keyword) :
                productRepository.findAll();

        model.addAttribute("keyword", keyword);
        model.addAttribute("productList", productList);
        return "products";
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/user/index";
    }

    @GetMapping("/admin/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public String delete(@RequestParam Long id) {
        System.out.println(" on delete : ");
        productRepository.deleteById(id);
        return "redirect:/user/index";
    }

    @GetMapping("/admin/newProduct")
    @PreAuthorize("hasRole('ADMIN')")
    public String newProduct(Model model) {
        System.out.println("  Voici ......................................................................");
        model.addAttribute("product", new Product());
        return "new-product";
    }

    @GetMapping("/admin/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public String editProduct(Long id, Model model) {
        System.out.println("  Voici EDIT 1 ......................................................................");
        Product product = productRepository.getReferenceById(id);
        model.addAttribute("product", product);

        return "new-product";
    }

    @PostMapping("/admin/saveProduct")
    @PreAuthorize("hasRole('ADMIN')")
    public String saveProduct(@Valid Product product, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "new-product";
        }

        productRepository.save(product);
        return "redirect:/user/index";
    }

    @GetMapping("/notAuthorized")
    public String notAuthorized() {
        return "notAuthorized";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "logout";
    }
}
