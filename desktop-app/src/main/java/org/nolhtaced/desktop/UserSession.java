package org.nolhtaced.desktop;

import org.nolhtaced.core.types.NolhtacedSession;
import org.nolhtaced.core.models.User;

import java.util.Optional;

public class UserSession implements NolhtacedSession {
    private static UserSession session;
    private User currentUser;

    private UserSession() {}

    public static UserSession getSession() {
        if (session == null) {
            session = new UserSession();
        }

        return session;
    }

    public Optional<User> getCurrentUser() {
        return Optional.ofNullable(currentUser);
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
}
