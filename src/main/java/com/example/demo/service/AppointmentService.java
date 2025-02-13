package com.example.demo.service;

import com.example.demo.dto.AppointmentDto;
import com.example.demo.model.Appointment;
import com.example.demo.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;


    public AppointmentService(AppointmentRepository appointmentRepository){
        this.appointmentRepository=appointmentRepository;
    }

    public List<Appointment> getAllAppointments(){
        return appointmentRepository.findAll();
    }


    public List<Appointment> findByTime(String time){
        return appointmentRepository.findByTime(time);
    }


    public boolean existsByDateAndTime(String date, String time){
        return appointmentRepository.existsByDateAndTime(date,time);
    }

    public void deleteByDateAndTime(String date, String time){
        appointmentRepository.deleteByDateAndTime(date,time);
    }

    public ResponseEntity<String> createAppointment(AppointmentDto appointmentDto){

        Appointment appointment = new Appointment();
        appointment.setDate(appointmentDto.getDate());
        appointment.setTime(appointmentDto.getTime());

        appointmentRepository.save(appointment);
        return ResponseEntity.status(HttpStatus.CREATED).body("Appointment Created Successfully...!");
    }
}
