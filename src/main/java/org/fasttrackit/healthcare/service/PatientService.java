package org.fasttrackit.healthcare.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fasttrackit.healthcare.domain.Patient;
import org.fasttrackit.healthcare.domain.Profile;
import org.fasttrackit.healthcare.exception.ResourceNotFoundException;
import org.fasttrackit.healthcare.persistance.PatientRepository;
import org.fasttrackit.healthcare.transfer.patient.GetPatientsRequest;
import org.fasttrackit.healthcare.transfer.patient.SavePatientRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PatientService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PatientService.class);

    private final PatientRepository patientRepository;
    private final ObjectMapper objectMapper;
    private final ProfileService profileService;

    @Autowired
    public PatientService(PatientRepository patientRepository, ObjectMapper objectMapper, ProfileService profileService) {
        this.patientRepository = patientRepository;
        this.objectMapper = objectMapper;
        this.profileService = profileService;
    }

    @Transactional
    public Patient createPatient(SavePatientRequest request) {
        LOGGER.info("Creating Patient {}", request);

        Profile profile = profileService.getProfile(request.getProfileId());
        Patient patient = objectMapper.convertValue(request, Patient.class);
        patient.setProfile(profile);

        return patientRepository.save(patient);
    }

    public Patient getPatient(long id) {
        LOGGER.info("Retrieving Patient {}", id);

        return patientRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Patient " + id + " not found."));
    }

    public Page<Patient> getPatients(GetPatientsRequest request, Pageable pageable) {
        LOGGER.info("Retrieving Patients {}", request);

        if (request != null) {
            if (request.getPartialFirstName() != null && request.getPartialLastName() != null) {
                return patientRepository.findByFirstNameContainingAndLastNameContaining(request.getPartialFirstName(),
                        request.getPartialLastName(), pageable);
            } else if (request.getPartialFirstName() != null) {
                return patientRepository.findByFirstNameContaining(request.getPartialFirstName(), pageable);
            } else if (request.getPartialLastName() != null) {
                return patientRepository.findByLastNameContaining(request.getPartialLastName(), pageable);
            }
        }
        return patientRepository.findAll(pageable);
    }

    public Patient updatePatient(long id, SavePatientRequest request) {
        LOGGER.info("Updating patient {}", id);
        Patient patient = getPatient(id);
        BeanUtils.copyProperties(request, patient);
        return patientRepository.save(patient);
    }

    public void deletePatient(long id) {
        LOGGER.info("Deleting patient {}", id);
        patientRepository.deleteById(id);
    }
}
