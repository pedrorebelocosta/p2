package org.nolhtaced.core.exceptions;

public class ObjectStillReferencedException extends Exception {
    public ObjectStillReferencedException() {
        super("Object is still referenced in other domain");
    }
}
