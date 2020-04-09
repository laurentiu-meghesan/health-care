package org.fasttrackit.healthcare;

import org.fasttrackit.healthcare.domain.Patient;
import org.fasttrackit.healthcare.domain.Profile;
import org.fasttrackit.healthcare.exception.ResourceNotFoundException;
import org.fasttrackit.healthcare.service.PatientService;
import org.fasttrackit.healthcare.steps.PatientTestSteps;
import org.fasttrackit.healthcare.steps.ProfileTestSteps;
import org.fasttrackit.healthcare.transfer.patient.SavePatientRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.TransactionSystemException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
public class PatientServiceIntegrationTest {

    @Autowired
    private PatientService patientService;
    @Autowired
    private PatientTestSteps patientTestSteps;
    @Autowired
    private ProfileTestSteps profileTestSteps;

    @Test
    void createPatient_whenValidRequest_thenPatientIsCreated() {
        patientTestSteps.createPatient();
    }

    @Test
    void createPatient_whenMissingBirthDate_thenExceptionIsThrown() {
        Profile profile = profileTestSteps.createProfile();

        SavePatientRequest request = new SavePatientRequest();
        request.setProfileId(profile.getId());
        request.setFirstName("Ion");
        request.setLastName("Ionesco");
        request.setPhoneNumber("077774484");

        try {
            patientService.createPatient(request);
        } catch (Exception e) {
            assertThat(request, notNullValue());
            assertThat("Unexpected exception type.", e instanceof TransactionSystemException);
        }
    }

    @Test
    void getPatient_whenExistingPatient_thenReturnPatient() {
        Patient patient = patientTestSteps.createPatient();

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
        Patient patient = patientTestSteps.createPatient();

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
        Patient patient = patientTestSteps.createPatient();

        patientService.deletePatient(patient.getId());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> patientService.getPatient(patient.getId()));
    }
}
