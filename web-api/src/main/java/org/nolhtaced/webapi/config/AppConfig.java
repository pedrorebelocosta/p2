package org.nolhtaced.webapi.config;

import lombok.RequiredArgsConstructor;
import org.nolhtaced.core.exceptions.UserNotFoundException;
import org.nolhtaced.core.models.Customer;
import org.nolhtaced.core.services.CustomerService;
import org.nolhtaced.core.utilities.PasswordUtil;
import org.nolhtaced.webapi.models.CustomerDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        CustomerService service = new CustomerService(null);

        return username -> {
            try {
                Customer byUsername = service.getByUsername(username);
                return new CustomerDetails(byUsername);
            } catch (UserNotFoundException e) {
                throw new UsernameNotFoundException(e.getMessage());
            }
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return PasswordUtil.generateHash(rawPassword.toString());
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return PasswordUtil.checkPassword(rawPassword.toString(), encodedPassword);
            }
        });

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
