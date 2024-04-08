package org.nolhtaced.core.exceptions;

public class UserNotFoundException extends Exception {
    public UserNotFoundException() {
        super("User was not found");
    }
}
