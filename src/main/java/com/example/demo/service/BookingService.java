package com.example.demo.service;

import com.example.demo.dto.AppointmentDto;
import com.example.demo.model.Booking;
import com.example.demo.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;


    public BookingService(BookingRepository bookingRepository){
        this.bookingRepository=bookingRepository;
    }

    public List<Booking> getBookings(){
        return bookingRepository.findAll();
    }


    public List<Booking> findByEmail(String email){
        return bookingRepository.findByEmail(email);
    }


    public boolean existsByDateAndTime(String date, String time){
        return bookingRepository.existsByDateAndTime(date,time);
    }


    public ResponseEntity<String> createBooking(AppointmentDto appointmentDto){

        Booking booking = new Booking();
        booking.setName(appointmentDto.getName());
        booking.setEmail(appointmentDto.getEmail());
        booking.setPhone(appointmentDto.getPhone());
        booking.setDate(appointmentDto.getDate());
        booking.setTime(appointmentDto.getTime());

        bookingRepository.save(booking);
        return ResponseEntity.status(HttpStatus.CREATED).body("Appointment Created Successfully...!");
    }

    public String cancelBooking(Long bookingId) {
        if (bookingRepository.existsById(bookingId)) {
            bookingRepository.deleteById(bookingId);
            return "Booking Deleted Successfully...!";
        } else {
            throw new RuntimeException("Booking not found");
        }
    }

    public Booking getById(Long bookingId) {
        return bookingRepository.getById(bookingId);
    }
}
