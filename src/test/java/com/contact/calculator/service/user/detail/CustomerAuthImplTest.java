package com.contact.calculator.service.user.detail;

import com.contact.calculator.model.entities.CustomerDetailsEntity;
import com.contact.calculator.model.entities.RoleEntity;
import com.contact.calculator.repository.CustomerDetailsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerAuthImplTest {

    @Mock
    private CustomerDetailsRepository customerDetailsRepository;
    @InjectMocks
    private CustomerAuthImpl customerAuth;


    @Test
    void loadUserByUsername() {

        when(customerDetailsRepository.findByEmail(any()))
                .thenReturn(CustomerDetailsEntity.builder()
                        .name("name")
                        .surname("surname")
                        .email("email@gmail.com")
                        .password("password")
                        .roles(Collections.singletonList(RoleEntity.builder()
                                .name("USER")
                                .build()))
                        .build());


        UserDetails userDetails = customerAuth.loadUserByUsername("email@gmail.com");
        Assertions.assertNotNull(userDetails);
    }

    @Test
    void testInvalidUsername(){
        UsernameNotFoundException usernameNotFoundException = Assertions.assertThrows(UsernameNotFoundException.class, () -> customerAuth.loadUserByUsername("email@gmail.com"));
        Assertions.assertEquals("Invalid email/username or password",usernameNotFoundException.getMessage());
    }
}