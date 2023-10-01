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
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    public void addImageToProduct(Image image){ // добавление картинки
        image.setTour(this);
        images.add(image);
    }
}


