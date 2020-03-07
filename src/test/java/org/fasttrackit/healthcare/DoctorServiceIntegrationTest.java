package org.fasttrackit.healthcare;

import org.fasttrackit.healthcare.domain.Doctor;
import org.fasttrackit.healthcare.service.DoctorService;
import org.fasttrackit.healthcare.transfer.SaveDoctorRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.TransactionSystemException;

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
    void createDoctor_whenMissingFirstName_thenExceptionIsThrown(){
        SaveDoctorRequest request = new SaveDoctorRequest();
        request.setLastName("Gigi");
        request.setPhoneNumber("0744747474");
        request.setOfficeAddress("Strada Lunga");

        try {
            doctorService.createDoctor(request);
        }catch (Exception e){
            assertThat(request, notNullValue());
            assertThat("Unexpected exception type.", e instanceof ConstraintViolationException);
        }
    }

    @Test
    void getDoctor_whenExistingDoctor_thenReturnDoctor(){
        Doctor doctor = createDoctor();

        Doctor response = doctorService.getDoctor(doctor.getId());

        assertThat(response, notNullValue());
        assertThat(response.getId(), is(doctor.getId()));
        assertThat(response.getFirstName(), is(doctor.getFirstName()));
        assertThat(response.getLastName(), is(doctor.getLastName()));
        assertThat(response.getPhoneNumber(), is(doctor.getPhoneNumber()));
        assertThat(response.getOfficeAddress(), is(doctor.getOfficeAddress()));
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
