package com.by.me.freeparty.Repositorys;


import com.by.me.freeparty.Model.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourRep extends JpaRepository<Tour,Integer> {

}
