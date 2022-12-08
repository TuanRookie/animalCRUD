package com.example.demo.DAO;


public class Pet {
    private int id;
    private String name;
    private String species;
    private int age;
    private boolean neutered;

    public Pet() {
    }

    public Pet(int id, String name, String species, int age, boolean neutered) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.age = age;
        this.neutered = neutered;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isNeutered() {
        return neutered;
    }

    public void setNeutered(boolean neutered) {
        this.neutered = neutered;
    }
}
