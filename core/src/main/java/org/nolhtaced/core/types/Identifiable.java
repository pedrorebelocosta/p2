package org.nolhtaced.core.types;

public class Identifiable<K> {
    private K id;

    public Identifiable() {
    }

    public Identifiable(K id) {
        this.id = id;
    }

    public K getId() {
        return id;
    }

    public void setId(K id) {
        this.id = id;
    }
}
