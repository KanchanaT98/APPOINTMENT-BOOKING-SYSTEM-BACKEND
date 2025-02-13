package com.example.demo.repository;

import com.example.demo.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    Booking getById(Long Id);
    List<Booking> findByEmail(String email);
    boolean existsByDateAndTime(String date, String time);
    List<Booking> findAll();

}
