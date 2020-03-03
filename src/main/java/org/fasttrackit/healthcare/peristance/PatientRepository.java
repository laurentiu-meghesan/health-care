package org.fasttrackit.healthcare.peristance;

import org.fasttrackit.healthcare.domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {

}
