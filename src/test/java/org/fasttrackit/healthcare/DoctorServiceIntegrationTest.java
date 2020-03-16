package org.fasttrackit.healthcare;

import org.fasttrackit.healthcare.domain.Doctor;
import org.fasttrackit.healthcare.exception.ResourceNotFoundException;
import org.fasttrackit.healthcare.service.DoctorService;
import org.fasttrackit.healthcare.transfer.doctor.SaveDoctorRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@SpringBootTest
public class DoctorServiceIntegrationTest {

    @Autowired
    private DoctorService doctorService;

    @Test
    void createDoctor_whenValidRequest_thenDoctorIsCreated() {

        createDoctor();
    }

    @Test
    void createDoctor_whenMissingFirstName_thenExceptionIsThrown() {
        SaveDoctorRequest request = new SaveDoctorRequest();
        request.setLastName("Gigi");
        request.setPhoneNumber("0744747474");
        request.setOfficeAddress("Strada Lunga");

        try {
            doctorService.createDoctor(request);
        } catch (Exception e) {
            assertThat(request, notNullValue());
            assertThat("Unexpected exception type.", e instanceof ConstraintViolationException);
        }
    }

    @Test
    void getDoctor_whenExistingDoctor_thenReturnDoctor() {
        Doctor doctor = createDoctor();

        Doctor response = doctorService.getDoctor(doctor.getId());

        assertThat(response, notNullValue());
        assertThat(response.getId(), is(doctor.getId()));
        assertThat(response.getFirstName(), is(doctor.getFirstName()));
        assertThat(response.getLastName(), is(doctor.getLastName()));
        assertThat(response.getPhoneNumber(), is(doctor.getPhoneNumber()));
        assertThat(response.getOfficeAddress(), is(doctor.getOfficeAddress()));
    }

    @Test
    void updateDoctor_whenExistingDoctor_thenReturnUpdatedDoctor() {
        Doctor doctor = createDoctor();

        SaveDoctorRequest request = new SaveDoctorRequest();
        request.setFirstName(doctor.getFirstName() + " updated.");
        request.setLastName(doctor.getLastName() + " updated.");
        request.setOfficeAddress(doctor.getOfficeAddress() + " updated.");
        request.setPhoneNumber(doctor.getPhoneNumber() + " updated.");

        Doctor updatedDoctor = doctorService.updateDoctor(doctor.getId(), request);

        assertThat(updatedDoctor, notNullValue());
        assertThat(updatedDoctor.getFirstName(), is(request.getFirstName()));
        assertThat(updatedDoctor.getLastName(), is(request.getLastName()));
        assertThat(updatedDoctor.getOfficeAddress(), is(request.getOfficeAddress()));
        assertThat(updatedDoctor.getPhoneNumber(), is(request.getPhoneNumber()));
    }

    @Test
    void deleteDoctor_whenExistingDoctor_thenDoctorDoesNotExistAnymore() {
        Doctor doctor = createDoctor();

        doctorService.deleteDoctor(doctor.getId());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> doctorService.getDoctor(doctor.getId()));
    }

    private Doctor createDoctor() {
        SaveDoctorRequest request = new SaveDoctorRequest();
        request.setFirstName("Iulian");
        request.setLastName("Ionescu");
        request.setPhoneNumber("+40744123456");
        request.setOfficeAddress("Calea Manastur nr 4");

        Doctor doctor = doctorService.createDoctor(request);

        assertThat(doctor, notNullValue());
        assertThat(doctor.getId(), greaterThan(0L));
        assertThat(doctor.getFirstName(), is(request.getFirstName()));
        assertThat(doctor.getLastName(), is(request.getLastName()));
        assertThat(doctor.getPhoneNumber(), is(request.getPhoneNumber()));
        assertThat(doctor.getOfficeAddress(), is(request.getOfficeAddress()));
        return doctor;
    }
}
