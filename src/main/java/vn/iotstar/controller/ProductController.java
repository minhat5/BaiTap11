package vn.iotstar.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.iotstar.entity.Product;
import vn.iotstar.repository.ProductRepository;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository repo;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("items", repo.findAll());
        return "products"; // templates/products.html
    }

    @PostMapping
    public String create(@RequestParam String name, @RequestParam Double price) {
        Product p = new Product();
        p.setName(name);
        p.setPrice(price);
        repo.save(p);
        return "redirect:/products";
    }
}
