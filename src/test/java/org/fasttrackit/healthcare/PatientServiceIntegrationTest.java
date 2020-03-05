package org.fasttrackit.healthcare;

import org.fasttrackit.healthcare.domain.Patient;
import org.fasttrackit.healthcare.service.PatientService;
import org.fasttrackit.healthcare.transfer.SavePatientRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
    void createPatient_whenValidRequest_thenPatientIsCreated(){
        SavePatientRequest request = new SavePatientRequest();
        request.setFirstName("Cristi");
        request.setLastName("Cristea");
        request.setPhoneNumber("0745890890");
        request.setBirthDate(LocalDate.of(1990,10,11));

        Patient patient = patientService.createPatient(request);

        assertThat(patient, notNullValue());
        assertThat(patient.getId(), greaterThan(0L));
        assertThat(patient.getFirstName(), is(request.getFirstName()));
        assertThat(patient.getLastName(),is(request.getLastName()));
        assertThat(patient.getPhoneNumber(), is(request.getPhoneNumber()));
        assertThat(patient.getBirthDate(), is(request.getBirthDate()));
    }
}
