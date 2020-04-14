package org.fasttrackit.healthcare.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fasttrackit.healthcare.domain.Doctor;
import org.fasttrackit.healthcare.domain.Profile;
import org.fasttrackit.healthcare.exception.ResourceNotFoundException;
import org.fasttrackit.healthcare.persistance.DoctorRepository;
import org.fasttrackit.healthcare.transfer.doctor.DoctorResponse;
import org.fasttrackit.healthcare.transfer.doctor.SaveDoctorRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class DoctorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DoctorService.class);

    private final DoctorRepository doctorRepository;
    private final ObjectMapper objectMapper;
    private final ProfileService profileService;

    @Autowired
    public DoctorService(DoctorRepository doctorRepository, ObjectMapper objectMapper, ProfileService profileService) {
        this.doctorRepository = doctorRepository;
        this.objectMapper = objectMapper;
        this.profileService = profileService;
    }

    @Transactional
    public DoctorResponse createDoctor(SaveDoctorRequest request) {
        LOGGER.info("Creating Doctor {}", request);

        Profile profile = profileService.getProfile(request.getProfileId());
        Doctor doctor = objectMapper.convertValue(request, Doctor.class);
        doctor.setProfile(profile);

        Doctor savedDoctor = doctorRepository.save(doctor);
        return mapDoctorResponse(savedDoctor);
    }

    public DoctorResponse getDoctor(long id) {
        LOGGER.info("Retrieving Doctor {}", id);

        Doctor doctor = findDoctor(id);

        return mapDoctorResponse(doctor);
    }

    public Doctor findDoctor(long id) {
        return doctorRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Doctor " + id + " not found."));
    }

    private DoctorResponse mapDoctorResponse(Doctor doctor) {
        DoctorResponse doctorDto = new DoctorResponse();
        doctorDto.setId(doctor.getId());
        doctorDto.setFirstName(doctor.getFirstName());
        doctorDto.setLastName(doctor.getLastName());
        doctorDto.setOfficeAddress(doctor.getOfficeAddress());
        doctorDto.setPhoneNumber(doctor.getPhoneNumber());
        return doctorDto;
    }

    public DoctorResponse updateDoctor(long id, SaveDoctorRequest request) {
        LOGGER.info("Updating doctor {}", id);
        Doctor doctor = findDoctor(id);

        BeanUtils.copyProperties(request, doctor);
        Doctor savedDoctor = doctorRepository.save(doctor);

        return mapDoctorResponse(savedDoctor);
    }

    public void deleteDoctor(long id) {
        LOGGER.info("Deleting doctor {}", id);
        doctorRepository.deleteById(id);
    }
}
