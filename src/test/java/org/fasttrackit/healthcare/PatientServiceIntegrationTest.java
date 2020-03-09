package org.fasttrackit.healthcare;

import org.fasttrackit.healthcare.domain.Patient;
import org.fasttrackit.healthcare.exception.ResourceNotFoundException;
import org.fasttrackit.healthcare.service.PatientService;
import org.fasttrackit.healthcare.transfer.SavePatientRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@SpringBootTest
public class PatientServiceIntegrationTest {

    @Autowired
    private PatientService patientService;

    @Test
    void createPatient_whenValidRequest_thenPatientIsCreated() {
        createPatient();
    }

    @Test
    void createPatient_whenMissingBirthDate_thenExceptionIsThrown() {
        SavePatientRequest request = new SavePatientRequest();
        request.setFirstName("Ion");
        request.setLastName("Ionesco");
        request.setPhoneNumber("077774484");

        try {
            patientService.createPatient(request);
        } catch (Exception e) {
            assertThat(request, notNullValue());
            assertThat("Unexpected exception type.", e instanceof ConstraintViolationException);
        }
    }

    @Test
    void getPatient_whenExistingPatient_thenReturnPatient() {
        Patient patient = createPatient();

        Patient response = patientService.getPatient(patient.getId());

        assertThat(patient, notNullValue());
        assertThat(response.getId(), is(patient.getId()));
        assertThat(response.getBirthDate(), is(patient.getBirthDate()));
        assertThat(response.getPhoneNumber(), is(patient.getPhoneNumber()));
        assertThat(response.getLastName(), is(patient.getLastName()));
        assertThat(response.getFirstName(), is(patient.getFirstName()));
    }

    @Test
    void getPatient_whenNonExistingPatient_thenThrowResourceNotFoundException() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> patientService.getPatient(43435343));
    }

    @Test
    void updatePatient_whenValidRequest_thenReturnUpdatedPatient() {
        Patient patient = createPatient();

        SavePatientRequest request = new SavePatientRequest();
        request.setFirstName(patient.getFirstName() + " updated.");
        request.setLastName(patient.getLastName() + " updated.");
        request.setPhoneNumber(patient.getPhoneNumber() + " updated.");
        request.setBirthDate(patient.getBirthDate().minusYears(2));

        Patient updatedPatient = patientService.updatePatient(patient.getId(), request);

        assertThat(updatedPatient, notNullValue());
        assertThat(updatedPatient.getId(), is(patient.getId()));
        assertThat(updatedPatient.getFirstName(), is(request.getFirstName()));
        assertThat(updatedPatient.getLastName(), is(request.getLastName()));
        assertThat(updatedPatient.getPhoneNumber(), is(request.getPhoneNumber()));
        assertThat(updatedPatient.getBirthDate(), is(request.getBirthDate()));
    }

    @Test
    void deletePatient_whenExistingPatient_thenPatientDoesNotExistAnymore() {
        Patient patient = createPatient();

        patientService.deletePatient(patient.getId());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> patientService.getPatient(patient.getId()));
    }

    private Patient createPatient() {
        SavePatientRequest request = new SavePatientRequest();
        request.setFirstName("Cristi");
        request.setLastName("Cristea");
        request.setPhoneNumber("0745890890");
        request.setBirthDate(LocalDate.of(1990, 10, 11));

        Patient patient = patientService.createPatient(request);

        assertThat(patient, notNullValue());
        assertThat(patient.getId(), greaterThan(0L));
        assertThat(patient.getFirstName(), is(request.getFirstName()));
        assertThat(patient.getLastName(), is(request.getLastName()));
        assertThat(patient.getPhoneNumber(), is(request.getPhoneNumber()));
        assertThat(patient.getBirthDate(), is(request.getBirthDate()));
        return patient;
    }
}
