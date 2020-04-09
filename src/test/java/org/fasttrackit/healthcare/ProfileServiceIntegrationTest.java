package org.fasttrackit.healthcare;

import org.fasttrackit.healthcare.domain.Profile;
import org.fasttrackit.healthcare.exception.ResourceNotFoundException;
import org.fasttrackit.healthcare.service.ProfileService;
import org.fasttrackit.healthcare.steps.ProfileTestSteps;
import org.fasttrackit.healthcare.transfer.profile.SaveProfileRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
public class ProfileServiceIntegrationTest {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ProfileTestSteps profileTestSteps;

    @Test
    void createProfile_whenValidRequest_thenProfileIsCreated() {
        profileTestSteps.createProfile();
    }

    @Test
    void createProfile_whenMissingUserId_thenExceptionIsThrown() {
        SaveProfileRequest request = new SaveProfileRequest();
        request.setDoctor(false);
        request.setEmail("abc@xyz.com");
        request.setUserName("Jonathan");
        request.setPassword("12345678");

        try {
            profileService.createProfile(request);
        } catch (Exception e) {
            assertThat(request, notNullValue());
            assertThat("Unexpected exception type.", e instanceof NullPointerException);
        }
    }

    @Test
    void getProfile_whenExistingProfile_thenReturnProfile() {
        Profile profile = profileTestSteps.createProfile();
        Profile response = profileService.getProfile(profile.getId());
        assertThat(response, notNullValue());
        assertThat(response.getId(), is(profile.getId()));
        assertThat(response.getUserName(), is(profile.getUserName()));
        assertThat(response.getPassword(), is(profile.getPassword()));
        assertThat(response.getEmail(), is(profile.getEmail()));
    }

    @Test
    void getProfile_whenNonExistingProfile_thenExceptionIsThrown() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> profileService.getProfile(500012));
    }

    @Test
    void updateProfile_whenValidRequest_thenReturnUpdatedProfile() {
        Profile profile = profileTestSteps.createProfile();

        SaveProfileRequest request = new SaveProfileRequest();
        request.setUserName(profile.getUserName() + " updated.");
        request.setPassword(profile.getPassword() + " updated.");
        request.setEmail(profile.getEmail() + " updated.");

        Profile updatedProfile = profileService.updateProfile(profile.getId(), request);

        assertThat(updatedProfile, notNullValue());
        assertThat(updatedProfile.getId(), is(profile.getId()));
        assertThat(updatedProfile.getUserName(), is(request.getUserName()));
        assertThat(updatedProfile.getPassword(), is(request.getPassword()));
        assertThat(updatedProfile.getEmail(), is(request.getEmail()));
    }

    @Test
    void deleteProfile_whenExistingProfile_thenProfileDoesNotExistAnymore() {
        Profile profile = profileTestSteps.createProfile();

        profileService.deleteProfile(profile.getId());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> profileService.getProfile(profile.getId()));
    }
}
