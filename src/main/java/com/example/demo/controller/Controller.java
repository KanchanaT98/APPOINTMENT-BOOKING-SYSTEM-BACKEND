package com.example.demo.controller;

import com.example.demo.dto.AppointmentDto;
import com.example.demo.dto.UserDto;
import com.example.demo.model.Appointment;
import com.example.demo.model.Booking;
import com.example.demo.service.AppointmentService;
import com.example.demo.service.BookingService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api")
public class Controller {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserService userService;

    @GetMapping("/appointment")         //send all the available appointments to the frontend to display
    public List<Appointment> availableAppointmentList(){
        return appointmentService.getAllAppointments();
    }

    @GetMapping("/mybookings")          //send all the personal bookings to the frontend to display
    public List<Booking> bookingList(){
        return bookingService.getBookings();
    }

    @GetMapping("/myBooking/{email}")       //not yet developed
    public List<Booking> bookedAppointmentList(@PathVariable String email){
        return bookingService.findByEmail(email);
    }

    @CrossOrigin("/booking/*")          //Create new booking
    @PostMapping("/booking")
    public String booking(@RequestBody AppointmentDto appointmentDto){
        try {
            if(userService.existsByEmail(appointmentDto.getEmail())) {
                if (bookingService.existsByDateAndTime(appointmentDto.getDate(), appointmentDto.getTime())) {
                    return "This Slot is Already Booked, Please try another Slot...";
                } else {
                    bookingService.createBooking(appointmentDto);
                    appointmentService.deleteByDateAndTime(appointmentDto.getDate(), appointmentDto.getTime());
                    return "Slot Successfully Booked...";
                }
            }else{
                return "Email not registered";
            }
        }catch (Exception e) {
            System.out.println(e);
            return "Something went wrong";
        }
    }

    @PostMapping("/appointment")  //Create new appointments, this must be done externally by internal site admins
    public String createAppointment(@RequestBody AppointmentDto appointmentDto){
        try {
            if (appointmentService.existsByDateAndTime(appointmentDto.getDate(), appointmentDto.getTime())) {
                return "This Slot is Already Booked, Please try another Slot...";
            } else {
                appointmentService.createAppointment(appointmentDto);
                return "Slot Successfully Booked...";
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return "Something went wrong";
    }

    @PostMapping("/signup")             //Create new users for the system
    public String createUser(@RequestBody UserDto userDto){
        try{
            if(userService.existsByEmail(userDto.getEmail())){
                return "Email is Already Taken";
            }
            System.out.println(userDto.getUserName());
            return userService.createUser(userDto);

        } catch (Exception e) {
            return "Something went wrong";
        }
    }

    @PostMapping("/login")              //handle the login verifications
    public String login(@RequestBody UserDto userDto){
        try{
            if(userService.existsByUserName(userDto.getUserName()) && userService.isPasswordMatch(userDto)){
                return "Login Successfull";
            } else{
                return "Invalid login credentials";
            }
        } catch (Exception e) {
            return "Something went wrong";
        }
    }

    @DeleteMapping("/appointments/{bookingId}")  //Cancel personal bookings and resend the slot to available appointments
    public String cancelBooking(@PathVariable Long bookingId){
        try {
            Booking b = bookingService.getById(bookingId);
            AppointmentDto a = new AppointmentDto();
            a.setDate(b.getDate());
            a.setTime(b.getTime());
            appointmentService.createAppointment(a);
            return bookingService.cancelBooking(bookingId);
        }catch (Exception e){
            System.out.println(e);
        }
        return "Something went wrong";
    }

}
