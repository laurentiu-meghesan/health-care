package org.fasttrackit.healthcare.persistance;

import org.fasttrackit.healthcare.domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {

}
