package org.nolhtaced.webapi.services;

import lombok.RequiredArgsConstructor;
import org.nolhtaced.core.models.Customer;
import org.nolhtaced.core.services.CustomerService;
import org.nolhtaced.webapi.controllers.AuthController;
import org.nolhtaced.webapi.security.JwtService;
import org.nolhtaced.webapi.models.auth.AuthResponse;
import org.nolhtaced.webapi.models.auth.SigninRequest;
import org.nolhtaced.webapi.models.auth.SignupRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    public AuthResponse register(SignupRequest body) {
        CustomerService service = new CustomerService(null);
        Customer customer = body.asCustomer();
        service.create(customer);
        String token = jwtService.generateToken(body.asCustomerDetails());
        return new AuthResponse(token);

    }

    public AuthResponse signin(SigninRequest req) {
        logger.debug("LOGGING IN USER " + req.username());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                req.username(),
                req.password()
        ));
        logger.debug("USER SUCCESSFULLY LOGGED IN");
        // assume sign in was successful all is well here
        UserDetails userDetails = userDetailsService.loadUserByUsername(req.username());
        String token = jwtService.generateToken(userDetails);
        return new AuthResponse(token);
    }
}
