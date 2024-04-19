package org.nolhtaced.core.enumerators;

public enum TransactionStateEnum {
    ORDERED("ordered") {
        @Override
        public String toString() {
            return "Ordered";
        }
    },
    AWAITING_PAYMENT("awaiting_payment") {
        @Override
        public String toString() {
            return "Awaiting Payment";
        }
    },
    PAYMENT_RECEIVED("payment_received") {
        @Override
        public String toString() {
            return "Payment Received";
        }
    },
    CONFIRMED("confirmed") {
        @Override
        public String toString() {
            return "Confirmed";
        }
    },
    PROCESSED("processed") {
        @Override
        public String toString() {
            return "Processed";
        }
    },
    SHIPPED("shipped") {
        @Override
        public String toString() {
            return "Shipped";
        }
    },
    DELIVERED("delivered") {
        @Override
        public String toString() {
            return "Delivered";
        }
    },
    DONE("done") {
        @Override
        public String toString() {
            return "Done";
        }
    },
    CANCELLED("cancelled") {
        @Override
        public String toString() {
            return "Cancelled";
        }
    };

    public final String value;

    TransactionStateEnum(String value) {
        this.value = value;
    }
}
