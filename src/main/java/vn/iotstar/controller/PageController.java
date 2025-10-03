package vn.iotstar.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping("/") public String home(){ return "index"; }
    @GetMapping("/login") public String login(){ return "login"; }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin") public String admin(Model model){
        model.addAttribute("msg","Hello Admin");
        return "admin";
    }
}