package com.by.me.freeparty.Services;

import com.by.me.freeparty.Model.Image;
import com.by.me.freeparty.Model.Person;
import com.by.me.freeparty.Model.Tour;
import com.by.me.freeparty.Repositorys.PersonRep;
import com.by.me.freeparty.Repositorys.TourRep;


import com.by.me.freeparty.Security.PersonDetails;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PreRemove;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor

public class TourServices {
    private final TourRep tourRep;
    private final PersonRep personRep;
    private final EntityManager entityManager;
    @Autowired
    private EmailSenderServices emailSenderServices;

    public static void addImg(Tour tour, MultipartFile file, Image image) throws IOException {
        if (file.getSize() != 0) {
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
    public static String getGender(Person person){
        String gender;
        if (person.getGender() == "male") {
            return "Уважаемый";
        } else return  "Уважаемая";
    }

    public static String sendGiftEmailToThePerson(Person person, Tour tour){
        String body = getGender(person) + " " + person.getUsername() + " вы бронировали тур в " + tour.getCountry() + "\n" + "К сожалению тур был отменен, приносим свои извинения " + "\n" +
                " в качестве извинений, предлагаем воспользоваться нашим промокдом на 50% скидку! Для получения промокода, свяжитесь с нашим менеджером "+"\n"
                + "С уважение Road To The Dream";
        return body;
    }
    public static boolean checkDayBeforeStart(Tour tour){
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime dayOfStartTour = tour.getDate();
        if(today.getDayOfMonth()== dayOfStartTour.getDayOfMonth())
            return true;
        else
        return false;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(int id) {
        Tour tour = tourRep.findById(id).orElseThrow(() -> new RuntimeException("тур не найден"));
        if(checkDayBeforeStart(tour)) {
            List<Person> persons = tour.getPersons();
            for (Person person : persons) {
                person.getTours().remove(tour);
               emailSenderServices.sendEmail(person.getEmail(), "отмена брони",sendGiftEmailToThePerson(person,tour));
            }
        }
            List<Person> persons = tour.getPersons();
            for (Person person : persons) {
                person.getTours().remove(tour);
                emailSenderServices.sendEmail(person.getEmail(), "бронь отменена", getGender(person) + " к сожалению ваш рейс был отменен " + "\n" + " приносим свои извинения " + "\n" + " с наилучшими пожаления Road To The Dream");
            }
            tour.getPersons().clear();


            tourRep.deleteById(id);

    }

    public List<Tour> findAll() {
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



    public static String sendMessageToPerson(Person person, Tour tour) {

        String body = getGender(person) + " " + person.getUsername() + " вы забронировали тур в " + tour.getCountry() + "\n" + "Благодарим вас за то что выбрали нас " + "\n" + "\n" + "С уважение Road To The Dream";
        String message = body;
        return message;
    }

    public static String sendRemoveTourMessageToPerson(Person person, Tour tour) {
        String body = getGender(person) + " " + person.getUsername() + " вы отменили бронирование тура в  " + tour.getCountry() + "\n" + "\n" + "\n" + "С уважение Road To The Dream";
        String message = body;
        return message;
    }

    public static String sendMessageToCompany(Person person, Tour tour) {
        String body = "Пользователь " + person.getEmail() + " забронировал тур в " + tour.getCountry() + " id тура " + tour.getId() + "\n" + " вам необходимо связаться с ним!";
        return body;

    }

    public Tour getOne(int id) {
        return tourRep.findById(id).orElse(null);
    }

    @Transactional
    public void addTourToUser(int id) {
        Tour tour = tourRep.findById(id).orElseThrow(() -> new RuntimeException("Такого тура нет"));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        Person person = personDetails.getPerson();
        List<Tour> tours = person.getTours();
        tours.add(tour);
        List<Person> persons = tour.getPersons();
        persons.add(person);
        tour.setPersons(persons);
        person.setTours(tours);
        tourRep.save(tour);
        personRep.save(person);
        emailSenderServices.sendEmail(person.getEmail(), "бронирование", sendMessageToPerson(person, tour));
        emailSenderServices.sendEmail("caplyginmihail48@gmail.com", "бронирование", sendMessageToCompany(person, tour));
    }

    @Transactional
    public void deleteTourFromUser(int id, int id2) {
        Tour tour = tourRep.findById(id).orElseThrow(() -> new RuntimeException("Такого тура нет"));
        Person person = personRep.findById(id2).orElseThrow(() -> new RuntimeException("Такого пользователя не найдено"));
        List<Tour> person_tours = person.getTours();
        person_tours.remove(tour);
        person.setTours(person_tours);
        emailSenderServices.sendEmail(person.getEmail(), "Отмена", sendRemoveTourMessageToPerson(person, tour));
        emailSenderServices.sendEmail("caplyginmihail48@gmail.com", "отмена", sendMessageToCompany(person, tour));
    }

    public List<Person> getAllGuests(int id) {
        Tour tour = tourRep.findById(id).orElseThrow(() -> new RuntimeException("такого тура нет"));
        if (tour.getPersons().isEmpty()) {
            return null;
        }
        return tour.getPersons();
    }

}
