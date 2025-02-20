package com.example.petfinder;

public class Pet {

    private int id;
    private String name;
    private String breed;
    private String age;
    private String description;
    private String reporterName;
    private String imageUrl;

    public Pet(int id, String name, String breed, String age, String description, String reporterName, String imageUrl) {
        this.id = id;
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.description = description;
        this.reporterName = reporterName;
        this.imageUrl = imageUrl;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBreed() {
        return breed;
    }

    public String getAge() {
        return age;
    }

    public String getDescription() {
        return description;
    }

    public String getReporterName() {
        return reporterName;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
