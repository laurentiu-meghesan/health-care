package org.fasttrackit.healthcare.persistance;

import org.fasttrackit.healthcare.domain.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
