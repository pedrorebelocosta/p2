package org.nolhtaced.core.models;

import org.nolhtaced.core.types.Identifiable;

public class Category extends Identifiable<Integer> {
    private String name;
    private String title;

    public Category() {
    }

    public Category(String name, String title) {
        this.name = name;
        this.title = title;
    }

    public Category(Integer id, String name, String title) {
        super(id);
        this.name = name;
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
