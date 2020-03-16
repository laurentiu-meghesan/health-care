package org.fasttrackit.healthcare.service;
import org.fasttrackit.healthcare.domain.Appointment;
import org.fasttrackit.healthcare.exception.ResourceNotFoundException;
import org.fasttrackit.healthcare.persistance.AppointmentRepository;
import org.fasttrackit.healthcare.transfer.appointment.GetAppointmentsRequest;
import org.fasttrackit.healthcare.transfer.appointment.SaveAppointmentRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        appointment.setPatientId(request.getPatientID());
        appointment.setSymptoms(request.getSymptoms());
        appointment.setDiagnostic(request.getDiagnostic());
        appointment.setTreatment(request.getTreatment());
        appointment.setRecommendations(request.getRecommendations());
        return appointmentRepository.save(appointment);
    }

    public Appointment getAppointment(long id){
        LOGGER.info("Retrieving Appointment {}", id);

        return appointmentRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Appointment " + id + " not found."));
    }

    public Page<Appointment> getAppointments(GetAppointmentsRequest request, Pageable pageable){
        LOGGER.info("Retrieving Appointments {}", request);

        if(request != null){
            if(request.getPatientId() != null){
                return appointmentRepository.findByPatientId(request.getPatientId(), pageable);
            } else if (request.getSearchDate() != null){
                return appointmentRepository.findByAppointmentDateOrderByAppointmentDate
                        (request.getSearchDate(), pageable);
            }
        }
        return appointmentRepository.findAll(pageable);
    }

    public Appointment updateAppointment(long id, SaveAppointmentRequest request){
        LOGGER.info("Updated appointment {}: {}", id, request);
        Appointment appointment = getAppointment(id);

        BeanUtils.copyProperties(request, appointment);
        return appointmentRepository.save(appointment);
    }

    public void deleteAppointment(long id){
        LOGGER.info("Deleting appointment {}", id);
        appointmentRepository.deleteById(id);
    }

}
