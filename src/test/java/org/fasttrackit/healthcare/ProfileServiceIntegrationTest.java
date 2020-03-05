package org.fasttrackit.healthcare;

import org.fasttrackit.healthcare.domain.Profile;
import org.fasttrackit.healthcare.service.ProfileService;
import org.fasttrackit.healthcare.transfer.SaveProfileRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@SpringBootTest
public class ProfileServiceIntegrationTest {

    @Autowired
    private ProfileService profileService;

    @Test
    void createProfile_whenValidRequest_thenProfileIsCreated(){
        SaveProfileRequest request = new SaveProfileRequest();
        request.setUserId(1L);
        request.setUserName("guru2005");
        request.setPassword("1234abcd");
        request.setEmail("abd@yahoo.com");
        request.setDoctor(false);

        Profile profile = profileService.createProfile(request);

        assertThat(profile, notNullValue());
        assertThat(profile.getId(), greaterThan(0L));
        assertThat(profile.getUserId(), is(request.getUserId()));
        assertThat(profile.getUserName(), is(request.getUserName()));
        assertThat(profile.getPassword(), is(request.getPassword()));
        assertThat(profile.getEmail(), is(request.getEmail()));
    }
}
