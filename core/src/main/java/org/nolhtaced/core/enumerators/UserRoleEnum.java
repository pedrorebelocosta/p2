package org.nolhtaced.core.enumerators;

public enum UserRoleEnum {
    ADMINISTRATOR {
        @Override
        public String toString() {
            return "A";
        }

        public String getLabel() {
            return "Administrator";
        }
    },
    CUSTOMER {
        @Override
        public String toString() {
            return "C";
        }

        public String getLabel() {
            return "Customer";
        }
    },
    MANAGER {
        @Override
        public String toString() {
            return "MA";
        }

        public String getLabel() {
            return "Manager";
        }
    },
    SALES_REPRESENTATIVE {
        @Override
        public String toString() {
            return "S";
        }

        public String getLabel() {
            return "Sales Representative";
        }
    },
    MECHANIC {
        @Override
        public String toString() {
            return "ME";
        }

        public String getLabel() {
            return "Mechanic";
        }
    }
}
