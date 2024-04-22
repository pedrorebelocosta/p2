package org.nolhtaced.core.enumerators;

public enum AppointmentTypeEnum {
    REPAIR_REQUEST("RR") {
        @Override
        public String toString() {
            return "Repair Request";
        }
    },
    REPAIR_PICKUP("RP") {
        @Override
        public String toString() {
            return "Repair Pickup";
        }
    },
    CONTACT_REQUEST("CR") {
        @Override
        public String toString() {
            return "Contact Request";
        }
    };

    public final String value;

    AppointmentTypeEnum(String value) {
        this.value = value;
    }
}
