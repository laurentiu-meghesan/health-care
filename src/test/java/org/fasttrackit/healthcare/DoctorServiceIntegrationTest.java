package org.fasttrackit.healthcare;

import org.fasttrackit.healthcare.domain.Doctor;
import org.fasttrackit.healthcare.domain.Profile;
import org.fasttrackit.healthcare.exception.ResourceNotFoundException;
import org.fasttrackit.healthcare.service.DoctorService;
import org.fasttrackit.healthcare.steps.DoctorTestSteps;
import org.fasttrackit.healthcare.steps.ProfileTestSteps;
import org.fasttrackit.healthcare.transfer.doctor.DoctorResponse;
import org.fasttrackit.healthcare.transfer.doctor.SaveDoctorRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.TransactionSystemException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
public class DoctorServiceIntegrationTest {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private ProfileTestSteps profileTestSteps;

    @Autowired
    private DoctorTestSteps doctorTestSteps;

    @Test
    void createDoctor_whenValidRequest_thenDoctorIsCreated() {

        doctorTestSteps.createDoctor();
    }

    @Test
    void createDoctor_whenMissingFirstName_thenExceptionIsThrown() {
        Profile profile = profileTestSteps.createProfile();

        SaveDoctorRequest request = new SaveDoctorRequest();
        request.setProfileId(profile.getId());
        request.setLastName("Ionescu");
        request.setPhoneNumber("+40744123456");
        request.setOfficeAddress("Calea Manastur nr 4");

        try {
            doctorService.createDoctor(request);
        } catch (Exception e) {
            assertThat(request, notNullValue());
            assertThat("Unexpected exception type.", e instanceof TransactionSystemException);
        }
    }

    @Test
    void getDoctor_whenExistingDoctor_thenReturnDoctor() {
        DoctorResponse doctor = doctorTestSteps.createDoctor();

        DoctorResponse response = doctorService.getDoctor(doctor.getId());

        assertThat(response, notNullValue());
        assertThat(response.getId(), is(doctor.getId()));
        assertThat(response.getFirstName(), is(doctor.getFirstName()));
        assertThat(response.getLastName(), is(doctor.getLastName()));
        assertThat(response.getPhoneNumber(), is(doctor.getPhoneNumber()));
        assertThat(response.getOfficeAddress(), is(doctor.getOfficeAddress()));
    }

    @Test
    void updateDoctor_whenExistingDoctor_thenReturnUpdatedDoctor() {
        DoctorResponse doctor = doctorTestSteps.createDoctor();

        SaveDoctorRequest request = new SaveDoctorRequest();
        request.setFirstName(doctor.getFirstName() + " updated.");
        request.setLastName(doctor.getLastName() + " updated.");
        request.setOfficeAddress(doctor.getOfficeAddress() + " updated.");
        request.setPhoneNumber(doctor.getPhoneNumber() + " updated.");

        DoctorResponse updatedDoctor = doctorService.updateDoctor(doctor.getId(), request);

        assertThat(updatedDoctor, notNullValue());
        assertThat(updatedDoctor.getFirstName(), is(request.getFirstName()));
        assertThat(updatedDoctor.getLastName(), is(request.getLastName()));
        assertThat(updatedDoctor.getOfficeAddress(), is(request.getOfficeAddress()));
        assertThat(updatedDoctor.getPhoneNumber(), is(request.getPhoneNumber()));
    }

    @Test
    void deleteDoctor_whenExistingDoctor_thenDoctorDoesNotExistAnymore() {
        DoctorResponse doctor = doctorTestSteps.createDoctor();

        doctorService.deleteDoctor(doctor.getId());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> doctorService.getDoctor(doctor.getId()));
    }
}
