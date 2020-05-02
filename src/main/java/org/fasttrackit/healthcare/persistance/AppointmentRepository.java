package org.fasttrackit.healthcare.persistance;

import org.fasttrackit.healthcare.domain.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Page<Appointment> findByPatientIdOrderByAppointmentDateDesc(Long patientId, Pageable pageable);

    Page<Appointment> findByAppointmentDateOrderByAppointmentDate(LocalDateTime searchDate, Pageable pageable);

}
