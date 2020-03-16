package org.fasttrackit.healthcare.service;
import org.fasttrackit.healthcare.domain.Patient;
import org.fasttrackit.healthcare.exception.ResourceNotFoundException;
import org.fasttrackit.healthcare.persistance.PatientRepository;
import org.fasttrackit.healthcare.transfer.patient.SavePatientRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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

    public Patient getPatient(long id){
        LOGGER.info("Retrieving Patient {}", id);

        return patientRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Patient " + id + " not found."));
    }

    public Patient updatePatient(long id, SavePatientRequest request){
        LOGGER.info("Updating patient {}", id);
        Patient patient = getPatient(id);
        BeanUtils.copyProperties(request,patient);
        return patientRepository.save(patient);
    }

    public void deletePatient(long id){
        LOGGER.info("Deleting patient {}", id);
        patientRepository.deleteById(id);
    }
}
