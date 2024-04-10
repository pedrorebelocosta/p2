package org.nolhtaced.core.models;

import org.nolhtaced.core.types.Identifiable;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(name, category.name) && Objects.equals(title, category.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, title);
    }
}
