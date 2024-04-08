package org.nolhtaced.core.models;

import org.nolhtaced.core.types.Identifiable;

public class Service extends Identifiable<Integer> {
    private String name;
    private String title;
    private Float price;

    public Service() {
    }

    public Service(String name, String title, Float price) {
        this.name = name;
        this.title = title;
        this.price = price;
    }

    public Service(Integer id, String name, String title, Float price) {
        this.setId(id);
        this.name = name;
        this.title = title;
        this.price = price;
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

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
