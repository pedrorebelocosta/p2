package org.nolhtaced.core;

import org.nolhtaced.core.models.User;

import java.util.Optional;

public interface NolhtacedSession {
    Optional<User> getCurrentUser();
    void setCurrentUser(User u);
}
