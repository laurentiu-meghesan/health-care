package org.fasttrackit.healthcare.service;
import org.fasttrackit.healthcare.domain.Appointment;
import org.fasttrackit.healthcare.persistance.AppointmentRepository;
import org.fasttrackit.healthcare.transfer.SaveAppointmentRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppointmentService.class);

    private final AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository){
        this.appointmentRepository = appointmentRepository;
    }

    public Appointment createAppointment(SaveAppointmentRequest request){
        LOGGER.info("Creating Appointment {}", request);
        Appointment appointment = new Appointment();
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setPatientID(request.getPatientID());
        appointment.setSymptoms(request.getSymptoms());
        appointment.setDiagnostic(request.getDiagnostic());
        appointment.setTreatment(request.getTreatment());
        appointment.setRecommendations(request.getRecommendations());
        return appointmentRepository.save(appointment);
    }
}
