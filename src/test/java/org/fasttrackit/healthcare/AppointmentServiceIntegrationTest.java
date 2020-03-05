package org.fasttrackit.healthcare;

import org.fasttrackit.healthcare.domain.Appointment;
import org.fasttrackit.healthcare.service.AppointmentService;
import org.fasttrackit.healthcare.transfer.SaveAppointmentRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.TransactionSystemException;

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
        SaveAppointmentRequest request = new SaveAppointmentRequest();
        request.setAppointmentDate(LocalDateTime.of
                (2020, 8, 10, 12, 30));
        request.setPatientID(1L);
        request.setSymptoms("febra");
        request.setDiagnostic("gripa");
        request.setTreatment("paracetamol");
        request.setRecommendations("ceai");

        Appointment appointment = appointmentService.createAppointment(request);

        assertThat(appointment, notNullValue());
        assertThat(appointment.getId(), greaterThan(0L));
        assertThat(appointment.getSymptoms(), is(request.getSymptoms()));
        assertThat(appointment.getDiagnostic(), is(request.getDiagnostic()));
        assertThat(appointment.getRecommendations(), is(request.getRecommendations()));
        assertThat(appointment.getAppointmentDate(), is(request.getAppointmentDate()));
    }

    @Test
    void createAppointment_whenMissingAppointmentDate_then_ExceptionIsThrown(){
        SaveAppointmentRequest request = new SaveAppointmentRequest();
        request.setPatientID(1L);
        request.setSymptoms("febra");
        request.setDiagnostic("gripa");
        request.setTreatment("paracetamol");
        request.setRecommendations("ceai");

        try {
            appointmentService.createAppointment(request);
        } catch (Exception e) {
            assertThat(e, notNullValue());
            assertThat("Unexpected exception type.", e instanceof TransactionSystemException);
        }
    }

}
