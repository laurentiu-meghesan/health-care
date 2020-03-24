package org.fasttrackit.healthcare.web;

import org.fasttrackit.healthcare.domain.Appointment;
import org.fasttrackit.healthcare.service.AppointmentService;
import org.fasttrackit.healthcare.transfer.appointment.GetAppointmentsRequest;
import org.fasttrackit.healthcare.transfer.appointment.SaveAppointmentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public ResponseEntity<Appointment> createAppointment(@Valid @RequestBody SaveAppointmentRequest request){

        Appointment appointment = appointmentService.createAppointment(request);
        return new ResponseEntity<>(appointment, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointment(@PathVariable long id){
        Appointment appointment = appointmentService.getAppointment(id);
        return new ResponseEntity<>(appointment, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<Appointment>> getAppointments(GetAppointmentsRequest request, Pageable pageable){
        Page<Appointment> appointments = appointmentService.getAppointments(request, pageable);
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable long id,@Valid @RequestBody SaveAppointmentRequest request){
        Appointment appointment = appointmentService.updateAppointment(id, request);
        return new ResponseEntity<>(appointment, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable long id){
        appointmentService.deleteAppointment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
