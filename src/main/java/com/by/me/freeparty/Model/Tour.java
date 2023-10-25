package com.by.me.freeparty.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CollectionIdJdbcTypeCode;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;

@Cacheable(false)
@Entity
@Table(name = "tour")
public class Tour {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "description")
    @NotEmpty(message = "заполните поле")
    @Length(min = 10, message = "введите нормально")
    private String description;

    @Column(name = "cost")
    @NotEmpty(message = "заполните поле")
    @Length(min = 1, max = 100, message = "неверно введенная цена")
    private String cost;
    @Column(name = "country")
    @NotEmpty(message = "заполните поле")
    private String country;

    private Long previewImageId;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "tour")
    private List<Image> images = new ArrayList<>();

    @ManyToMany(mappedBy = "tours", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Person> persons = new ArrayList<>();
    @Column(name = "date")

    private LocalDateTime date;


    public void addImageToProduct(Image image) { // добавление картинки
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }



    public Tour() {
    }

    public Tour(int id, String description, String cost, String country, Long previewImageId, List<Image> images, List<Person> persons, LocalDateTime date) {
        this.id = id;
        this.description = description;
        this.cost = cost;
        this.country = country;
        this.previewImageId = previewImageId;
        this.images = images;
        this.persons = persons;
        this.date = date;

    }
}


