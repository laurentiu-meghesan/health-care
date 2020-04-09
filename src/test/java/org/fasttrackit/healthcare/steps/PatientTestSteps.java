package org.fasttrackit.healthcare.steps;

import org.fasttrackit.healthcare.domain.Patient;
import org.fasttrackit.healthcare.domain.Profile;
import org.fasttrackit.healthcare.service.PatientService;
import org.fasttrackit.healthcare.transfer.patient.SavePatientRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@Component
public class PatientTestSteps {

    @Autowired
    private PatientService patientService;
    @Autowired
    private ProfileTestSteps profileTestSteps;

    public Patient createPatient() {
        Profile profile = profileTestSteps.createProfile();
        profile.setDoctor(false);

        SavePatientRequest request = new SavePatientRequest();
        request.setProfileId(profile.getId());
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
