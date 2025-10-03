package vn.iotstar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.iotstar.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserInfoDetailService implements UserDetailsService {
    private final UserRepository repo;
    @Override public UserDetails loadUserByUsername(String username) {
        return repo.findByUsername(username)
                .map(UserInfoUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}