package org.fasttrackit.healthcare.web;

import org.fasttrackit.healthcare.service.DoctorService;
import org.fasttrackit.healthcare.transfer.doctor.DoctorResponse;
import org.fasttrackit.healthcare.transfer.doctor.SaveDoctorRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping
    public ResponseEntity<DoctorResponse> createDoctor(@Valid @RequestBody SaveDoctorRequest request) {
        DoctorResponse doctor = doctorService.createDoctor(request);
        return new ResponseEntity<>(doctor, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponse> getDoctor(@PathVariable long id) {
        DoctorResponse doctor = doctorService.getDoctor(id);
        return new ResponseEntity<>(doctor, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorResponse> updateDoctor(@PathVariable long id, @Valid @RequestBody SaveDoctorRequest request) {
        DoctorResponse doctor = doctorService.updateDoctor(id, request);
        return new ResponseEntity<>(doctor, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable long id) {
        doctorService.deleteDoctor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
