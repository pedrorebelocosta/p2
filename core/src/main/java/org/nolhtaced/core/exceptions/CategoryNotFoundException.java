package org.nolhtaced.core.exceptions;

public class CategoryNotFoundException extends Exception {
    public CategoryNotFoundException() {
        super("Category not found");
    }
}
