package org.fasttrackit.healthcare.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fasttrackit.healthcare.domain.Patient;
import org.fasttrackit.healthcare.domain.Profile;
import org.fasttrackit.healthcare.exception.ResourceNotFoundException;
import org.fasttrackit.healthcare.persistance.PatientRepository;
import org.fasttrackit.healthcare.transfer.patient.GetPatientsRequest;
import org.fasttrackit.healthcare.transfer.patient.PatientResponse;
import org.fasttrackit.healthcare.transfer.patient.SavePatientRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

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
    public PatientResponse createPatient(SavePatientRequest request) {
        LOGGER.info("Creating Patient {}", request);

        Profile profile = profileService.getProfile(request.getProfileId());
        Patient patient = objectMapper.convertValue(request, Patient.class);
        patient.setProfile(profile);

        Patient savedPatient = patientRepository.save(patient);
        return mapPatientResponse(savedPatient);
    }

    public PatientResponse getPatient(long id) {
        LOGGER.info("Retrieving Patient {}", id);

        Patient patient = findPatient(id);

        return mapPatientResponse(patient);
    }

    public Patient findPatient(long id) {
        return patientRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Patient " + id + " not found."));
    }

    private PatientResponse mapPatientResponse(Patient patient) {
        PatientResponse patientDto = new PatientResponse();
        patientDto.setId(patient.getId());
        patientDto.setFirstName(patient.getFirstName());
        patientDto.setLastName(patient.getLastName());
        patientDto.setBirthDate(patient.getBirthDate());
        patientDto.setPhoneNumber(patient.getPhoneNumber());
        return patientDto;
    }

    public Page<PatientResponse> getPatients(GetPatientsRequest request, Pageable pageable) {
        LOGGER.info("Retrieving Patients {}", request);

        Page<Patient> patientsPage;
        if (request != null) {
            if (request.getPartialFirstName() != null && request.getPartialLastName() != null) {
                patientsPage = patientRepository.findByFirstNameContainingAndLastNameContaining(request.getPartialFirstName(),
                        request.getPartialLastName(), pageable);
            } else if (request.getPartialFirstName() != null) {
                patientsPage = patientRepository.findByFirstNameContaining(request.getPartialFirstName(), pageable);
            } else if (request.getPartialLastName() != null) {
                patientsPage = patientRepository.findByLastNameContaining(request.getPartialLastName(), pageable);
            } else {
                patientsPage = patientRepository.findAll(pageable);
            }
        } else {
            patientsPage = patientRepository.findAll(pageable);
        }

        List<PatientResponse> patientDtos = new ArrayList<>();

        for (Patient patient : patientsPage.getContent()) {
            PatientResponse patientDto = mapPatientResponse(patient);

            patientDtos.add(patientDto);
        }

        return new PageImpl<>(patientDtos, pageable, patientsPage.getTotalElements());
    }

    public PatientResponse updatePatient(long id, SavePatientRequest request) {
        LOGGER.info("Updating patient {}", id);
        Patient patient = findPatient(id);

        BeanUtils.copyProperties(request, patient);
        Patient savedPatient = patientRepository.save(patient);

        return mapPatientResponse(savedPatient);
    }

    public void deletePatient(long id) {
        LOGGER.info("Deleting patient {}", id);
        patientRepository.deleteById(id);
    }
}
