package org.nolhtaced.core.enumerators;

public enum RepairStateEnum {
    PENDING("pending") {
        @Override
        public String toString() {
            return "Pending";
        }
    },
    IN_PROGRESS("in_progress") {
        @Override
        public String toString() {
            return "In Progress";
        }
    },
    ON_HOLD("on_hold") {
        @Override
        public String toString() {
            return "On Hold";
        }
    },
    COMPLETED("completed") {
        @Override
        public String toString() {
            return "Completed";
        }
    },
    CANCELLED("cancelled") {
        @Override
        public String toString() {
            return "Cancelled";
        }
    },
    AWAITING_PAYMENT("awaiting_payment") {
        @Override
        public String toString() {
            return "Awaiting Payment";
        }
    },
    QUALITY_CHECK("quality_check") {
        @Override
        public String toString() {
            return "Quality Check";
        }
    },
    DELIVERED("delivered") {
        @Override
        public String toString() {
            return "Delivered";
        }
    },
    REOPENED("reopened") {
        @Override
        public String toString() {
            return "Reopened";
        }
    };

    public final String value;

    RepairStateEnum(String value) {
        this.value = value;
    }
}
