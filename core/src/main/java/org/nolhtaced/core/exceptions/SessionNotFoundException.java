package org.nolhtaced.core.exceptions;

public class SessionNotFoundException extends Exception {
    public SessionNotFoundException() {
        super("Could not find a session, please make sure to set the session before invoking any method that requires authentication");
    }
}
