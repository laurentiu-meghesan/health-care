package org.fasttrackit.healthcare.persistance;

import org.fasttrackit.healthcare.domain.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Page<Patient> findByFirstNameContainingAndLastNameContaining(String partialFirstName, String partialLastName, Pageable pageable);

    Page<Patient> findByFirstNameContaining(String partialFirstName, Pageable pageable);

    Page<Patient> findByLastNameContaining(String partialLastName, Pageable pageable);

}
