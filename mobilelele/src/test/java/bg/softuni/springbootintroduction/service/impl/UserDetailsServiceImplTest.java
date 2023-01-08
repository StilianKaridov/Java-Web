package bg.softuni.springbootintroduction.service.impl;

import bg.softuni.springbootintroduction.domain.entity.User;
import bg.softuni.springbootintroduction.domain.entity.UserRole;
import bg.softuni.springbootintroduction.repository.UserRepository;
import bg.softuni.springbootintroduction.utils.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    private UserRepository mockedUserRepository;

    private UserDetailsService toTest;

    @BeforeEach
    void setUp() {
        this.toTest = new UserDetailsServiceImpl(
                mockedUserRepository
        );
    }

    @Test
    void test_LoadUserByUsername_UserExists() {
        User userEntity = new User(
                "Peter01",
                "peter",
                "Peter",
                "Petrov",
                true,
                new UserRole(Role.USER),
                "http://someImage.com/image",
                Instant.now(),
                Instant.now());

        when(this.mockedUserRepository.findUserByUsername(userEntity.getUsername()))
                .thenReturn(Optional.of(userEntity));

        UserDetails userDetails = this.toTest.loadUserByUsername(userEntity.getUsername());

        assertEquals(userEntity.getUsername(), userDetails.getUsername());
        assertEquals(userEntity.getPassword(), userDetails.getPassword());

        boolean areAuthoritiesEqual = false;

        for (GrantedAuthority auth : userDetails.getAuthorities()) {
            String userEntityRoleName = "ROLE_" + userEntity.getRole().getRole().name();

            areAuthoritiesEqual = auth.getAuthority().equals(userEntityRoleName);
        }

        assertTrue(areAuthoritiesEqual);
    }

    @Test
    void test_LoadUserByUsername_UserDoesNotExist() {
        String nonExistingUserUsername = "non-existing-user";

        assertThrows(
                UsernameNotFoundException.class,
                () -> this.toTest.loadUserByUsername(nonExistingUserUsername)
        );
    }
}