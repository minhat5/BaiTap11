package vn.iotstar.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserApiController {
    @GetMapping("/me")
    public Map<String,Object> me(Authentication auth){
        return Map.of("username", auth.getName(), "authorities", auth.getAuthorities());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<String> all(){ return List.of("u1","u2","u3"); }
}