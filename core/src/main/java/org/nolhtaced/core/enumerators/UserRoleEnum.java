package org.nolhtaced.core.enumerators;

public enum UserRoleEnum {
    ADMINISTRATOR("A") {
        @Override
        public String toString() {
            return "Administrator";
        }
    },
    CUSTOMER("C") {
        @Override
        public String toString() {
            return "Customer";
        }
    },
    MANAGER("MA") {
        @Override
        public String toString() {
            return "Manager";
        }
    },
    SALES_REPRESENTATIVE("S") {
        @Override
        public String toString() {
            return "Sales Representative";
        }
    },
    MECHANIC("ME") {
        @Override
        public String toString() {
            return "Mechanic";
        }
    };

    public final String value;

    UserRoleEnum(String value) {
        this.value = value;
    }
}
