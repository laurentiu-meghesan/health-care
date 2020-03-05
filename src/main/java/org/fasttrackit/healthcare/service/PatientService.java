package org.fasttrackit.healthcare.service;
import org.fasttrackit.healthcare.domain.Patient;
import org.fasttrackit.healthcare.persistance.PatientRepository;
import org.fasttrackit.healthcare.transfer.SavePatientRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PatientService.class);

    private final PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Patient createPatient(SavePatientRequest request) {
        LOGGER.info("Creating Patient {}", request);
        Patient patient = new Patient();
        patient.setFirstName(request.getFirstName());
        patient.setLastName(request.getLastName());
        patient.setBirthDate(request.getBirthDate());
        patient.setPhoneNumber(request.getPhoneNumber());
        return patientRepository.save(patient);
    }
}
