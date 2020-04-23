package org.fasttrackit.healthcare;

import org.fasttrackit.healthcare.domain.Appointment;
import org.fasttrackit.healthcare.domain.Doctor;
import org.fasttrackit.healthcare.domain.Patient;
import org.fasttrackit.healthcare.domain.Profile;
import org.fasttrackit.healthcare.persistance.AppointmentRepository;
import org.fasttrackit.healthcare.service.AppointmentService;
import org.fasttrackit.healthcare.service.DoctorService;
import org.fasttrackit.healthcare.service.PatientService;
import org.fasttrackit.healthcare.transfer.appointment.SaveAppointmentRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AppointmentServiceUnitTest {

    private AppointmentService appointmentService;

    @Mock
    private AppointmentRepository appointmentRepository;
    @Mock
    private PatientService patientService;
    @Mock
    private DoctorService doctorService;

    @Before
    public void setup() {
        appointmentService = new AppointmentService(appointmentRepository, patientService, doctorService);
    }

    @Test
    public void createAppointment() {

        Profile patientProfile = new Profile();
        patientProfile.setId(2L);
        patientProfile.setUserName("user");
        patientProfile.setPassword("pass");
        patientProfile.setEmail("email@email.email");
        patientProfile.setDoctor(false);

        Profile docProfile = new Profile();
        docProfile.setId(4L);
        docProfile.setUserName("doc");
        docProfile.setPassword("pass");
        docProfile.setEmail("email@doc.email");
        docProfile.setDoctor(true);

        Patient patient = new Patient();
        patient.setId(patientProfile.getId());
        patient.setFirstName("FirstName");
        patient.setLastName("LastName");
        patient.setPhoneNumber("0744666888");
        patient.setBirthDate(LocalDate.of(1990, 10, 15));
        patient.setProfile(patientProfile);

        Doctor doctor = new Doctor();
        doctor.setId(docProfile.getId());
        doctor.setFirstName("Doc");
        doctor.setLastName("Abc");
        doctor.setPhoneNumber("0777333222");
        doctor.setOfficeAddress("Adres bla bla");
        doctor.setProfile(docProfile);

        when(patientService.findPatient(anyLong())).thenReturn(patient);
        when(doctorService.findDoctor(anyLong())).thenReturn(doctor);

        Appointment appointment = new Appointment();
        appointment.setId(5);
        appointment.setAppointmentDate(LocalDateTime.of(2020, 4, 10, 12, 30));
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setSymptoms("febra");
        appointment.setDiagnostic("gripa");
        appointment.setTreatment("paracetamol");
        appointment.setRecommendations("ceai");

        when(appointmentRepository.save(any(Appointment.class))).thenReturn(null);

        SaveAppointmentRequest request = new SaveAppointmentRequest();
        request.setDoctorId(doctor.getId());
        request.setPatientId(patient.getId());
        request.setAppointmentDate(appointment.getAppointmentDate());
        request.setSymptoms(appointment.getSymptoms());
        request.setDiagnostic(appointment.getDiagnostic());
        request.setTreatment(appointment.getTreatment());
        request.setRecommendations(appointment.getRecommendations());

        appointmentService.createAppointment(request);

        verify(patientService).findPatient(anyLong());
        verify(doctorService).findDoctor(anyLong());
        verify(appointmentRepository).save(any(Appointment.class));
    }
}