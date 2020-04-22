package org.fasttrackit.healthcare;

import org.fasttrackit.healthcare.domain.Profile;
import org.fasttrackit.healthcare.persistance.ProfileRepository;
import org.fasttrackit.healthcare.service.ProfileService;
import org.fasttrackit.healthcare.transfer.profile.SaveProfileRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProfileServiceUnitTests {

    private ProfileService profileService;

    @Mock
    private ProfileRepository profileRepository;

    @Before
    public void setup(){
        profileService = new ProfileService(profileRepository);
    }

    @Test
    public void createProfile(){

        Profile profile = new Profile();
        profile.setId(2L);
        profile.setUserName("user");
        profile.setPassword("pass");
        profile.setEmail("email@email.email");
        profile.setDoctor(false);

        when(profileRepository.save(any(Profile.class))).thenReturn(null);

        SaveProfileRequest request = new SaveProfileRequest();
        request.setUserName(profile.getUserName());
        request.setPassword(profile.getPassword());
        request.setEmail(profile.getEmail());
        request.setDoctor(profile.isDoctor());

        profileService.createProfile(request);

        verify(profileRepository).save(any(Profile.class));
    }
}
