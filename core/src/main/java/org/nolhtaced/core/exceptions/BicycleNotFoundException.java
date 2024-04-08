package org.nolhtaced.core.exceptions;

public class BicycleNotFoundException extends Exception {
    public BicycleNotFoundException() {
        super("Bicycle not found");
    }
}
