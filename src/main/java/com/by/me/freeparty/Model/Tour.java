package com.by.me.freeparty.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CollectionIdJdbcTypeCode;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "tour")
public class Tour {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "description")
    private String description;
    @Column(name = "cost")
    private String cost;
    @Column(name = "country")
    private String country;

    private Long previewImageId;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER,
            mappedBy = "tour")
    private List<Image> images = new ArrayList<>();

    @ManyToMany(mappedBy = "tours", fetch = FetchType.EAGER,  cascade = CascadeType.REMOVE)
    private List<Person> persons = new ArrayList<>();

    public void addImageToProduct(Image image){ // добавление картинки
        image.setTour(this);
        images.add(image);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Long getPreviewImageId() {
        return previewImageId;
    }

    public void setPreviewImageId(Long previewImageId) {
        this.previewImageId = previewImageId;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public Tour() {
    }

    public Tour(int id, String description, String cost, String country, Long previewImageId, List<Image> images, List<Person> persons) {
        this.id = id;
        this.description = description;
        this.cost = cost;
        this.country = country;
        this.previewImageId = previewImageId;
        this.images = images;
        this.persons = persons;
    }
}


