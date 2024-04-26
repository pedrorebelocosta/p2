package org.nolhtaced.core.enumerators;

// https://www.baeldung.com/java-enum-values
// try to make this cleaner
public enum BicycleTypeEnum {
    ROAD("R") {
        @Override
        public String toString() {
            return "Road";
        }
    },
    MOUNTAIN("M") {
        @Override
        public String toString() {
            return "Mountain";
        }
    },
    GRAVEL("G") {
        @Override
        public String toString() {
            return "Gravel";
        }
    };

    public final String value;

    BicycleTypeEnum(String value) {
        this.value = value;
    }
}
