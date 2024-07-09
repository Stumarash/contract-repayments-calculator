package com.contact.calculator.service.user.detail;

import com.contact.calculator.model.entities.CustomerDetailsEntity;
import com.contact.calculator.repository.CustomerDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Primary
@RequiredArgsConstructor
public class CustomerAuthImpl implements UserDetailsService {

    private final CustomerDetailsRepository customerDetailsRepository;

    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final CustomerDetailsEntity customerDetailsEntity = customerDetailsRepository.findByEmail(username);
        if (Objects.nonNull(customerDetailsEntity)) {
            return new org.springframework.security.core.userdetails.User(customerDetailsEntity.getEmail(), customerDetailsEntity.getPassword(), customerDetailsEntity.getRoles().stream().map((role) -> new SimpleGrantedAuthority(role.getName())).toList());
        } else {
            throw new UsernameNotFoundException("Invalid email/username or password");
        }
    }
}
