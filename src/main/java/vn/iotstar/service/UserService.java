package vn.iotstar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.iotstar.entity.Role;
import vn.iotstar.entity.User;
import vn.iotstar.repository.RoleRepository;
import vn.iotstar.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository users;
    private final RoleRepository roles;
    private final PasswordEncoder encoder;

    @Transactional
    public User register(String username, String email, String rawPassword, String roleName){
        User u = new User();
        u.setUsername(username); u.setEmail(email);
        u.setPassword(encoder.encode(rawPassword)); u.setEnabled(true);
        Role role = roles.findByName(roleName)
                .orElseGet(() -> roles.save(new Role(null, roleName)));
        u.getRoles().add(role);
        return users.save(u);
    }
}