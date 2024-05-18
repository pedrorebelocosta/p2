package org.nolhtaced.desktop;

import org.nolhtaced.core.exceptions.UserNotFoundException;
import org.nolhtaced.core.models.Customer;
import org.nolhtaced.core.services.CustomerService;

public class Test {
    public static void main(String[] args) {
        CustomerService service = new CustomerService(null);

        try {
            Customer byUsername = service.getByUsername("ines2");
            System.out.println(byUsername.getUsername());
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
