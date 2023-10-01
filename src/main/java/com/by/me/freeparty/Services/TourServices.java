package com.by.me.freeparty.Services;

import com.by.me.freeparty.Model.Image;
import com.by.me.freeparty.Model.Tour;
import com.by.me.freeparty.Repositorys.TourRep;


import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor

public class TourServices {
    private final TourRep tourRep;
    public static void addImg(Tour tour, MultipartFile file, Image image) throws IOException {
        if(file.getSize()!=0){
            image = toImageEntity(file);
            tour.addImageToProduct(image);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void addTour(Tour tour, MultipartFile file1) throws IOException {
        Image image1;

        if (file1.getSize() != 0) {
            image1 = toImageEntity(file1);
            image1.setPreviewImage(true);
            tour.addImageToProduct(image1);
        }

        Tour tourFromDb = tourRep.save(tour);
        tourFromDb.setPreviewImageId(tourFromDb.getImages().get(0).getId());
            tourRep.save(tour);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(int id){
        tourRep.deleteById(id);
    }

    public List<Tour> findAll(){
        return tourRep.findAll();
    }


    private static Image toImageEntity(MultipartFile file1) throws IOException { //конвертирование фотографии в сущность
        Image image = new Image();
        image.setName(file1.getName());
        image.setOriginalFilename(file1.getOriginalFilename());
        image.setContentType(file1.getContentType());
        image.setSize(file1.getSize());
        image.setBytes(file1.getBytes());
        return image;
    }

    public Tour getOne(int id){
        return tourRep.findById(id).orElse(null);
    }



}
