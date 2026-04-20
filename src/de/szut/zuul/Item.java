package de.szut.zuul;

public class Item {
    private String name;
    private String description;
    private double weight;


    public Item(String name, String description, double weight) {
        this.name = name;
        this.description = description;
        this.weight = weight;
    }

    public String toString()
    {
        return description + ", " + weight + "kg [" + name + "]";
    }

    public String getName() {
        return this.name;
    }

    public double getWeight() {
        return this.weight;
    }
}
