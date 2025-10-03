package vn.iotstar.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.iotstar.config.JwtService;
import vn.iotstar.model.AuthRequest;
import vn.iotstar.model.AuthResponse;
import vn.iotstar.model.RegisterRequest;
import vn.iotstar.service.UserService;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthApiController {
    private final AuthenticationManager authManager;
    private final JwtService jwt;
    private final UserService userService;
    private final UserDetailsService uds;

    @PostMapping("/register")
    public Map<String,Object> register(@RequestBody RegisterRequest r){
        var u = userService.register(r.getUsername(), r.getEmail(), r.getPassword(), "ROLE_USER");
        return Map.of("id", u.getId(), "username", u.getUsername());
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest req){
        authManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
        UserDetails user = uds.loadUserByUsername(req.getUsername());
        return new AuthResponse(jwt.generateToken(user));
    }
}
