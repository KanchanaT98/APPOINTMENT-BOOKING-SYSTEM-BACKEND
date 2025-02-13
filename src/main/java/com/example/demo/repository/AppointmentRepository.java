package com.example.demo.repository;

import com.example.demo.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Appointment getById(Long Id);
    List<Appointment> findByTime(String time);
    boolean existsByDateAndTime(String date, String time);

    @Modifying          //custom created query for deletion
    @Transactional
    @Query("DELETE FROM Appointment a WHERE a.date = :date AND a.time = :time")
    void deleteByDateAndTime(@Param("date") String date, @Param("time") String time);

}
