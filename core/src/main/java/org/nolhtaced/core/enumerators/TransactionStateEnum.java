package org.nolhtaced.core.enumerators;

public enum TransactionStateEnum {
    ORDERED {
        @Override
        public String toString() {
            return "ordered";
        }
    },
    AWAITING_PAYMENT {
        @Override
        public String toString() {
            return "awaiting_payment";
        }
    },
    PAYMENT_RECEIVED {
        @Override
        public String toString() {
            return "payment_received";
        }
    },
    CONFIRMED {
        @Override
        public String toString() {
            return "confirmed";
        }
    },
    PROCESSED {
        @Override
        public String toString() {
            return "processed";
        }
    },
    SHIPPED {
        @Override
        public String toString() {
            return "shipped";
        }
    },
    DELIVERED {
        @Override
        public String toString() {
            return "delivered";
        }
    },
    DONE {
        @Override
        public String toString() {
            return "done";
        }
    },
    CANCELLED {
        @Override
        public String toString() {
            return "cancelled";
        }
    }
}
