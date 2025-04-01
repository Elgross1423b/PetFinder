package com.example.petfinder;

public class Pet {
    private int id;
    private String name;
    private String breed;
    private String age;
    private String description;
    private String reporterName;
    private String imageUrl;
    private String date; // Nuevo campo para la fecha

    // Constructor completo
    public Pet(int id, String name, String breed, String age,
               String description, String reporterName, String imageUrl) {
        this.id = id;
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.description = description;
        this.reporterName = reporterName;
        this.imageUrl = imageUrl;
        this.date = ""; // Inicializado vacío
    }

    // Constructor sin ID (útil para inserts)
    public Pet(String name, String breed, String age,
               String description, String reporterName, String imageUrl) {
        this(-1, name, breed, age, description, reporterName, imageUrl);
    }

    // Getters y Setters
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

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReporterName() {
        return reporterName;
    }

    public void setReporterName(String reporterName) {
        this.reporterName = reporterName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    // Método toString para depuración
    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", breed='" + breed + '\'' +
                ", age='" + age + '\'' +
                ", reporterName='" + reporterName + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}