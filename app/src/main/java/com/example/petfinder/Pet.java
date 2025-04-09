package com.example.petfinder;

public class Pet {
    private int id;
    private String name;
    private String breed;
    private String age;
    private String description;
    private String reporterName;
    private String imageUrl;
    private String date;
    private String location;
    private String status;

    public Pet(int id, String name, String breed, String age,
               String description, String reporterName, String imageUrl) {
        this.id = id;
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.description = description;
        this.reporterName = reporterName;
        this.imageUrl = imageUrl;
    }

    // Getters y setters para todos los campos
    public int getId() { return id; }
    public String getName() { return name; }
    public String getBreed() { return breed; }
    public String getAge() { return age; }
    public String getDescription() { return description; }
    public String getReporterName() { return reporterName; }
    public String getImageUrl() { return imageUrl; }
    public String getDate() { return date; }
    public String getLocation() { return location; }
    public String getStatus() { return status; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setBreed(String breed) { this.breed = breed; }
    public void setAge(String age) { this.age = age; }
    public void setDescription(String description) { this.description = description; }
    public void setReporterName(String reporterName) { this.reporterName = reporterName; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public void setDate(String date) { this.date = date; }
    public void setLocation(String location) { this.location = location; }
    public void setStatus(String status) { this.status = status; }
}