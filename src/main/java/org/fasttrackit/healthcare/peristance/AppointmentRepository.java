package org.fasttrackit.healthcare.peristance;

import org.fasttrackit.healthcare.domain.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
