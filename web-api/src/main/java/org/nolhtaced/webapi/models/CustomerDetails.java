package org.nolhtaced.webapi.models;

import lombok.Builder;
import org.nolhtaced.core.enumerators.UserRoleEnum;
import org.nolhtaced.core.models.Customer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomerDetails extends Customer implements UserDetails {
    public CustomerDetails(Customer c) {
        super(
                c.getId(),
                c.getUsername(),
                c.getPassword(),
                c.getFirstName(),
                c.getLastName(),
                c.getDateOfBirth(),
                c.getActive(),
                c.getDiscountRate(),
                c.getAddress(),
                c.getVatNo(),
                c.getBicycles(),
                c.getTransactions()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.getRole().value));
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.getActive();
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.getActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.getActive();
    }

    @Override
    public boolean isEnabled() {
        return this.getActive();
    }
}
