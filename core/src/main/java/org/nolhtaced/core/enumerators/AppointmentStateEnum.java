package org.nolhtaced.core.enumerators;

public enum AppointmentStateEnum {
    PENDING("pending") {
        @Override
        public String toString() {
            return "Pending";
        }
    },
    HANDLING("handling") {
        @Override
        public String toString() {
            return "Handling";
        }
    },
    HANDLED("handled") {
        @Override
        public String toString() {
            return "Handled";
        }
    },
    CLOSED("closed") {
        @Override
        public String toString() {
            return "Closed";
        }
    };

    public final String value;

    AppointmentStateEnum(String value) {
        this.value = value;
    }
}
