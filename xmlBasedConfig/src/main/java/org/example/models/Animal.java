package org.example.models;

public class Animal {
    private String species;
    private String name;
    private String texture;
    private int age;

    public Animal(){}

    public Animal(String species, String name, String texture, int age){
        this.species = species;
        this.name = name;
        this.texture = texture;
        this.age = age;
    }

    public void setSpecies(String species){
        this.species = species;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setTexture(String texture){
        this.texture = texture;
    }
    public void setAge(int age){
        this.age = age;
    }

    public String getSpecies(){
        return species;
    }
    public String getName(){
        return name;
    }
    public String getTexture(){
        return texture;
    }
    public int getAge(){
        return age;
    }
}
