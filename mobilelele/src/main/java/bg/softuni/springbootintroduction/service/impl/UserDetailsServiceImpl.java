package bg.softuni.springbootintroduction.service.impl;


import bg.softuni.springbootintroduction.domain.entity.User;
import bg.softuni.springbootintroduction.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final String ROLE_PREFIX = "ROLE_";

    private static final String EXCEPTION_MESSAGE = "User with name %s not found!";

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.
                findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(EXCEPTION_MESSAGE, username)));

        return mapToUserDetails(user);
    }

    private UserDetails mapToUserDetails(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        String userRole = user.getRole().getRole().name();

        authorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + userRole));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }
}
