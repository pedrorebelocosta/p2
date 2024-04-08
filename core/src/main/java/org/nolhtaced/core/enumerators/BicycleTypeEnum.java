package org.nolhtaced.core.enumerators;

public enum BicycleTypeEnum {
    ROAD {
        @Override
        public String toString() {
            return "R";
        }
    },
    MOUNTAIN {
        @Override
        public String toString() {
            return "M";
        }
    },
    GRAVEL {
        @Override
        public String toString() {
            return "G";
        }
    }
}
