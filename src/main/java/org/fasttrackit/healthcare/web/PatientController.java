package org.fasttrackit.healthcare.web;

import org.fasttrackit.healthcare.service.PatientService;
import org.fasttrackit.healthcare.transfer.patient.GetPatientsRequest;
import org.fasttrackit.healthcare.transfer.patient.PatientResponse;
import org.fasttrackit.healthcare.transfer.patient.SavePatientRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping
    public ResponseEntity<PatientResponse> createPatient(@Valid @RequestBody SavePatientRequest request) {
        PatientResponse patient = patientService.createPatient(request);
        return new ResponseEntity<>(patient, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponse> getPatient(@PathVariable long id) {
        PatientResponse patient = patientService.getPatient(id);
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<PatientResponse>> getPatients(GetPatientsRequest request, Pageable pageable) {
        Page<PatientResponse> patients = patientService.getPatients(request, pageable);
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientResponse> updatePatient(@PathVariable long id, @Valid @RequestBody SavePatientRequest request) {
        PatientResponse patient = patientService.updatePatient(id, request);
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable long id) {
        patientService.deletePatient(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
