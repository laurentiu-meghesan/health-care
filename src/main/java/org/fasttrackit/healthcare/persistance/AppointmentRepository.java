package org.fasttrackit.healthcare.persistance;

import org.fasttrackit.healthcare.domain.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Page<Appointment> findByPatientId(Long patientId, Pageable pageable);
}
