package org.nolhtaced.core.exceptions;

public class AppointmentNotFoundException extends Exception {
    public AppointmentNotFoundException() {
        super("Appointment couldn't be found");
    }
}
