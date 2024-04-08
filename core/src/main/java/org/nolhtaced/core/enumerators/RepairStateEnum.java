package org.nolhtaced.core.enumerators;

public enum RepairStateEnum {
    PENDING {
        @Override
        public String toString() {
            return "pending";
        }
    },
    IN_PROGRESS {
        @Override
        public String toString() {
            return "in_progress";
        }
    },
    ON_HOLD {
        @Override
        public String toString() {
            return "on_hold";
        }
    },
    COMPLETED {
        @Override
        public String toString() {
            return "completed";
        }
    },
    CANCELLED {
        @Override
        public String toString() {
            return "cancelled";
        }
    },
    AWAITING_PAYMENT {
        @Override
        public String toString() {
            return "awaiting_payment";
        }
    },
    QUALITY_CHECK {
        @Override
        public String toString() {
            return "quality_check";
        }
    },
    DELIVERED {
        @Override
        public String toString() {
            return "delivered";
        }
    },
    REOPENED {
        @Override
        public String toString() {
            return "reopened";
        }
    }
}
