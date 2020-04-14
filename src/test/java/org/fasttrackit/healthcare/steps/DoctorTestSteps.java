package org.fasttrackit.healthcare.steps;

import org.fasttrackit.healthcare.domain.Doctor;
import org.fasttrackit.healthcare.domain.Profile;
import org.fasttrackit.healthcare.service.DoctorService;
import org.fasttrackit.healthcare.transfer.doctor.DoctorResponse;
import org.fasttrackit.healthcare.transfer.doctor.SaveDoctorRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@Component
public class DoctorTestSteps {

    @Autowired
    private DoctorService doctorService;
    @Autowired
    private ProfileTestSteps profileTestSteps;

    public DoctorResponse createDoctor() {
        Profile profile = profileTestSteps.createProfile();

        SaveDoctorRequest request = new SaveDoctorRequest();
        request.setProfileId(profile.getId());
        request.setFirstName("Iulian");
        request.setLastName("Ionescu");
        request.setPhoneNumber("+40744123456");
        request.setOfficeAddress("Calea Manastur nr 4");

        DoctorResponse doctor = doctorService.createDoctor(request);

        assertThat(doctor, notNullValue());
        assertThat(doctor.getId(), greaterThan(0L));
        assertThat(doctor.getFirstName(), is(request.getFirstName()));
        assertThat(doctor.getLastName(), is(request.getLastName()));
        assertThat(doctor.getPhoneNumber(), is(request.getPhoneNumber()));
        assertThat(doctor.getOfficeAddress(), is(request.getOfficeAddress()));
        return doctor;
    }
}
