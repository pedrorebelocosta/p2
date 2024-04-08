package org.nolhtaced.core.exceptions;

public class ServiceNotFoundException extends Exception {
    public ServiceNotFoundException() {
        super("Service not found");
    }
}
