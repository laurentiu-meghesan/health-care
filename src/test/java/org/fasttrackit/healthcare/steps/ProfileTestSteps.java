package org.fasttrackit.healthcare.steps;

import org.fasttrackit.healthcare.domain.Profile;
import org.fasttrackit.healthcare.service.ProfileService;
import org.fasttrackit.healthcare.transfer.profile.SaveProfileRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@Component
public class ProfileTestSteps {

    @Autowired
    private ProfileService profileService;

    public Profile createProfile() {
        SaveProfileRequest request = new SaveProfileRequest();
        request.setUserName("guru2005");
        request.setPassword("1234abcd");
        request.setEmail("abd@yahoo.com");
        request.setDoctor(true);

        Profile profile = profileService.createProfile(request);

        assertThat(profile, notNullValue());
        assertThat(profile.getId(), greaterThan(0L));
        assertThat(profile.getUserName(), is(request.getUserName()));
        assertThat(profile.getPassword(), is(request.getPassword()));
        assertThat(profile.getEmail(), is(request.getEmail()));
        return profile;
    }
}
