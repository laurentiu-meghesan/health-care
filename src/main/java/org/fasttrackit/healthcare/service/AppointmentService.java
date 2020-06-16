package org.fasttrackit.healthcare.service;

import org.fasttrackit.healthcare.domain.Appointment;
import org.fasttrackit.healthcare.domain.Doctor;
import org.fasttrackit.healthcare.domain.Patient;
import org.fasttrackit.healthcare.exception.ResourceNotFoundException;
import org.fasttrackit.healthcare.persistance.AppointmentRepository;
import org.fasttrackit.healthcare.transfer.appointment.AppointmentResponse;
import org.fasttrackit.healthcare.transfer.appointment.GetAllAppointmentsRequest;
import org.fasttrackit.healthcare.transfer.appointment.SaveAppointmentRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppointmentService.class);

    private final AppointmentRepository appointmentRepository;
    private final PatientService patientService;
    private final DoctorService doctorService;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository, PatientService patientService, DoctorService doctorService) {
        this.appointmentRepository = appointmentRepository;
        this.patientService = patientService;
        this.doctorService = doctorService;
    }

    @Transactional
    public AppointmentResponse createAppointment(SaveAppointmentRequest request) {
        LOGGER.info("Creating Appointment {}", request);

        Patient patient = patientService.findPatient(request.getPatientId());
        Doctor doctor = doctorService.findDoctor(request.getDoctorId());

        Appointment appointment = new Appointment();
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setSymptoms(request.getSymptoms());
        appointment.setDiagnostic(request.getDiagnostic());
        appointment.setTreatment(request.getTreatment());
        appointment.setRecommendations(request.getRecommendations());
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);

        Appointment savedAppointment = appointmentRepository.save(appointment);

        return mapAppointmentResponse(savedAppointment);
    }

    public AppointmentResponse getAppointment(long id) {
        LOGGER.info("Retrieving Appointment {}", id);

        Appointment appointment = findAppointment(id);

        return mapAppointmentResponse(appointment);
    }

    public Appointment findAppointment(long id) {
        return appointmentRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Appointment " + id + " not found."));
    }

    private AppointmentResponse mapAppointmentResponse(Appointment appointment) {
        AppointmentResponse appointmentDto = new AppointmentResponse();
        appointmentDto.setId(appointment.getId());
        appointmentDto.setPatientId(appointment.getPatient().getId());
        appointmentDto.setDoctorId(appointment.getDoctor().getId());
        appointmentDto.setAppointmentDate(appointment.getAppointmentDate());
        appointmentDto.setSymptoms(appointment.getSymptoms());
        appointmentDto.setDiagnostic(appointment.getDiagnostic());
        appointmentDto.setTreatment(appointment.getTreatment());
        appointmentDto.setRecommendations(appointment.getRecommendations());
        return appointmentDto;
    }

    @Transactional
    public Page<AppointmentResponse> getAppointments(long patientId, Pageable pageable) {
        LOGGER.info("Retrieving Appointments for patient {}", patientId);

        Page<Appointment> appointmentsPage = appointmentRepository.
                findByPatientIdOrderByAppointmentDateDesc(patientId, pageable);

        List<AppointmentResponse> appointmentDtos = new ArrayList<>();

        for (Appointment appointment : appointmentsPage.getContent()) {
            AppointmentResponse appointmentDto = mapAppointmentResponse(appointment);

            appointmentDtos.add(appointmentDto);
        }
        return new PageImpl<>(appointmentDtos, pageable, appointmentsPage.getTotalElements());
    }

    @Transactional
    public Page<AppointmentResponse> getAllAppointments(Pageable pageable) {
        LOGGER.info("Retrieving all Appointments");

        Page<Appointment> appointmentsPage = appointmentRepository.findAll(pageable);

        List<AppointmentResponse> appointmentDtos = new ArrayList<>();

        for (Appointment appointment : appointmentsPage.getContent()) {
            AppointmentResponse appointmentDto = mapAppointmentResponse(appointment);

            appointmentDtos.add(appointmentDto);
        }
        return new PageImpl<>(appointmentDtos, pageable, appointmentsPage.getTotalElements());
    }

    public AppointmentResponse updateAppointment(long id, SaveAppointmentRequest request) {
        LOGGER.info("Updated appointment {}: {}", id, request);
        Appointment appointment = findAppointment(id);

        BeanUtils.copyProperties(request, appointment);
        Appointment savedAppointment = appointmentRepository.save(appointment);

        return mapAppointmentResponse(savedAppointment);
    }

    public void deleteAppointment(long id) {
        LOGGER.info("Deleting appointment {}", id);
        appointmentRepository.deleteById(id);
    }

}
