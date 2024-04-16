package org.nolhtaced.core.enumerators;

// https://www.baeldung.com/java-enum-values
// try to make this cleaner
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
    };
}
