package org.nolhtaced.desktop.exceptions;

public class UIManagerNotInitializedException extends Exception {
    public UIManagerNotInitializedException() {
        super("UIManager not initialized, initialize it by calling UIManager.initialize");
    }
}
