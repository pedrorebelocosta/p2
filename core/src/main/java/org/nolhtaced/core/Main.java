package org.nolhtaced.core;

import org.nolhtaced.core.exceptions.UserNotFoundException;
import org.nolhtaced.core.exceptions.ObjectStillReferencedException;
import org.nolhtaced.core.models.User;
import org.nolhtaced.core.services.CustomerService;
import org.nolhtaced.core.services.TransactionService;
import org.nolhtaced.core.types.NolhtacedSession;

import java.util.Optional;

public class Main {
    static class Sessao implements NolhtacedSession {
        private User currentUser;

        @Override
        public Optional<User> getCurrentUser() {
            return Optional.ofNullable(currentUser);
        }

        @Override
        public void setCurrentUser(User u) {
            currentUser = u;
        }
    }

    public static void main(String[] args) {
        Sessao s = new Sessao();
        CustomerService service = new CustomerService(s);
        try {
            System.out.println(service.getByUsername("ines2"));

        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
