package org.nolhtaced.webapi.models.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.nolhtaced.core.models.Customer;
import org.nolhtaced.webapi.models.CustomerDetails;

import java.time.LocalDate;
import java.util.ArrayList;

public record SignupRequest(
        String username,
        String password,
        @JsonProperty("first_name") String firstName,
        @JsonProperty("last_name") String lastName,
        @JsonProperty("date_of_birth") LocalDate dateOfBirth,
        Boolean active,
        @JsonProperty("discount_rate") Float discountRate,
        String address,
        @JsonProperty("vat_no") Integer vatNo
) {
    public Customer asCustomer() {
        return new Customer(
                username,
                password,
                firstName,
                lastName,
                dateOfBirth,
                active,
                discountRate,
                address,
                vatNo,
                new ArrayList<>(),
                new ArrayList<>()
        );
    }

    public CustomerDetails asCustomerDetails() {
        return new CustomerDetails(this.asCustomer());
    }
}
