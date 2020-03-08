package org.fasttrackit.healthcare.service;
import org.fasttrackit.healthcare.domain.Doctor;
import org.fasttrackit.healthcare.exception.ResourceNotFoundException;
import org.fasttrackit.healthcare.persistance.DoctorRepository;
import org.fasttrackit.healthcare.transfer.SaveDoctorRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DoctorService.class);

    private final DoctorRepository doctorRepository;

    @Autowired
    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public Doctor createDoctor(SaveDoctorRequest request){
        LOGGER.info("Creating Doctor {}", request);
        Doctor doctor = new Doctor();
        doctor.setFirstName(request.getFirstName());
        doctor.setLastName(request.getLastName());
        doctor.setPhoneNumber(request.getPhoneNumber());
        doctor.setOfficeAddress(request.getOfficeAddress());
        return doctorRepository.save(doctor);
    }

    public Doctor getDoctor(long id){
        LOGGER.info("Retrieving Doctor {}", id);

        return doctorRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Doctor " + id + " not found."));
    }

    public Doctor updateDoctor(long id, SaveDoctorRequest request){
        LOGGER.info("Updating doctor {}", id);
        Doctor doctor = getDoctor(id);
        BeanUtils.copyProperties(request, doctor);
        return doctorRepository.save(doctor);
    }

    public void deleteDoctor(long id){
        LOGGER.info("Deleting doctor {}", id);
        doctorRepository.deleteById(id);
    }
}
