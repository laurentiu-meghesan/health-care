package org.fasttrackit.healthcare;

import org.fasttrackit.healthcare.exception.ResourceNotFoundException;
import org.fasttrackit.healthcare.service.AppointmentService;
import org.fasttrackit.healthcare.transfer.appointment.AppointmentResponse;
import org.fasttrackit.healthcare.transfer.appointment.SaveAppointmentRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@SpringBootTest
public class AppointmentServiceIntegrationTest {

    @Autowired
    private AppointmentService appointmentService;

    @Test
    void createAppointment_whenValidRequest_thenAppointmentIsCreated() {
        createAppointment();
    }

    @Test
    void createAppointment_whenMissingAppointmentDate_thenExceptionIsThrown() {
        SaveAppointmentRequest request = new SaveAppointmentRequest();
        request.setPatientId(13L);
        request.setDoctorId(7L);
        request.setSymptoms("febra");
        request.setDiagnostic("gripa");
        request.setTreatment("paracetamol");
        request.setRecommendations("ceai");

        try {
            appointmentService.createAppointment(request);
        } catch (Exception e) {
            assertThat(e, notNullValue());
            assertThat("Unexpected exception type.", e instanceof ConstraintViolationException);
        }
    }

    @Test
    void getAppointment_whenExistingAppointment_thenReturnAppointment() {
        AppointmentResponse appointment = createAppointment();

        AppointmentResponse response = appointmentService.getAppointment(appointment.getId());

        assertThat(response, notNullValue());
        assertThat(response.getId(), is(appointment.getId()));
        assertThat(response.getAppointmentDate(), is(appointment.getAppointmentDate()));
        assertThat(response.getSymptoms(), is(appointment.getSymptoms()));
        assertThat(response.getDiagnostic(), is(appointment.getDiagnostic()));
        assertThat(response.getTreatment(), is(appointment.getTreatment()));
        assertThat(response.getRecommendations(), is(appointment.getRecommendations()));
    }

    @Test
    void getAppointment_whenNonExistingAppointment_thenThrowResourceNotFoundException() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> appointmentService.getAppointment(101256565L));
    }

    @Test
    void updateAppointment_whenValidRequest_thenUpdateAppointment() {
        AppointmentResponse appointment = createAppointment();

        SaveAppointmentRequest request = new SaveAppointmentRequest();
        request.setTreatment(appointment.getTreatment() + " updated.");
        request.setRecommendations(appointment.getRecommendations() + " updated.");
        request.setDiagnostic(appointment.getDiagnostic() + " updated.");
        request.setSymptoms(appointment.getSymptoms() + " updated.");
        request.setAppointmentDate(appointment.getAppointmentDate().plusDays(10));

        AppointmentResponse updatedAppointment = appointmentService.updateAppointment(appointment.getId(), request);

        assertThat(updatedAppointment, notNullValue());
        assertThat(updatedAppointment.getId(), is(appointment.getId()));
        assertThat(updatedAppointment.getAppointmentDate(), is(request.getAppointmentDate()));
        assertThat(updatedAppointment.getSymptoms(), is(request.getSymptoms()));
        assertThat(updatedAppointment.getDiagnostic(), is(request.getDiagnostic()));
        assertThat(updatedAppointment.getTreatment(), is(request.getTreatment()));
        assertThat(updatedAppointment.getRecommendations(), is(request.getRecommendations()));
    }

    @Test
    void deleteAppointment_whenExistingAppointment_thenTheAppointmentDoesNotExistAnymore() {
        AppointmentResponse appointment = createAppointment();

        appointmentService.deleteAppointment(appointment.getId());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> appointmentService.getAppointment(appointment.getId()));
    }

    private AppointmentResponse createAppointment() {
        SaveAppointmentRequest request = new SaveAppointmentRequest();
        request.setAppointmentDate(LocalDateTime.of
                (2020, 8, 10, 12, 30));
        request.setPatientId(13L);
        request.setDoctorId(7L);
        request.setSymptoms("febra");
        request.setDiagnostic("gripa");
        request.setTreatment("paracetamol");
        request.setRecommendations("ceai");

        AppointmentResponse appointment = appointmentService.createAppointment(request);

        assertThat(appointment, notNullValue());
        assertThat(appointment.getId(), greaterThan(0L));
        assertThat(appointment.getSymptoms(), is(request.getSymptoms()));
        assertThat(appointment.getDiagnostic(), is(request.getDiagnostic()));
        assertThat(appointment.getRecommendations(), is(request.getRecommendations()));
        assertThat(appointment.getAppointmentDate(), is(request.getAppointmentDate()));
        return appointment;
    }
}
