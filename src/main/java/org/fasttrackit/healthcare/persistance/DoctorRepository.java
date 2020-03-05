package org.fasttrackit.healthcare.persistance;

import org.fasttrackit.healthcare.domain.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
