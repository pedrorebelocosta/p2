package org.nolhtaced.core.models;

import org.nolhtaced.core.enumerators.BicycleTypeEnum;
import org.nolhtaced.core.types.Identifiable;

import java.util.List;

public class Bicycle extends Identifiable<Integer> {
    private Integer ownerId;
    private String name;
    private String model;
    private String brand;
    private BicycleTypeEnum type;
    private List<Repair> repairs;

    public Bicycle() {
    }

    public Bicycle(Integer ownerId, String name, String model, String brand, BicycleTypeEnum type, List<Repair> repairs) {
        this.ownerId = ownerId;
        this.name = name;
        this.model = model;
        this.brand = brand;
        this.type = type;
        this.repairs = repairs;
    }

    public Bicycle(Integer id, Integer ownerId, String name, String model, String brand, BicycleTypeEnum type, List<Repair> repairs) {
        super(id);
        this.ownerId = ownerId;
        this.name = name;
        this.model = model;
        this.brand = brand;
        this.type = type;
        this.repairs = repairs;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public BicycleTypeEnum getType() {
        return type;
    }

    public void setType(BicycleTypeEnum type) {
        this.type = type;
    }

    public List<Repair> getRepairs() {
        return repairs;
    }

    public void setRepairs(List<Repair> repairs) {
        this.repairs = repairs;
    }
}
