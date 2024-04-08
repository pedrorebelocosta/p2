package org.nolhtaced.core.exceptions;

public class EmployeeNotFoundException extends Exception {
    public EmployeeNotFoundException() {
        super("Employee not found");
    }
}
