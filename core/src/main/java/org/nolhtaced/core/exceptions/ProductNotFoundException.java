package org.nolhtaced.core.exceptions;

public class ProductNotFoundException extends Exception {
    public ProductNotFoundException() {
        super("Product not found");
    }
}
