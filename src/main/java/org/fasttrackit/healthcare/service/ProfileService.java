package org.fasttrackit.healthcare.service;

import org.fasttrackit.healthcare.domain.Profile;
import org.fasttrackit.healthcare.exception.ResourceNotFoundException;
import org.fasttrackit.healthcare.persistance.ProfileRepository;
import org.fasttrackit.healthcare.transfer.profile.GetProfilesRequest;
import org.fasttrackit.healthcare.transfer.profile.SaveProfileRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileService.class);

    private final ProfileRepository profileRepository;

    @Autowired
    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Profile createProfile(SaveProfileRequest request) {
        LOGGER.info("Creating Profile {}", request);
        Profile profile = new Profile();
        profile.setUserName(request.getUserName());
        profile.setPassword(request.getPassword());
        profile.setEmail(request.getEmail());
        profile.setDoctor(request.isDoctor());
        return profileRepository.save(profile);
    }

    public Profile getProfile(long id) {
        LOGGER.info("Retrieving Profile {}", id);
        return profileRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Profile " + id + " not found."));
    }

    public Profile loginProfile(String userName, String password){
        LOGGER.info("Retrieving user {}", userName);
        return profileRepository.findByUserNameEqualsAndPasswordEquals(userName, password);
    }

    public Page<Profile> getProfiles(GetProfilesRequest request, Pageable pageable) {
        LOGGER.info("Retrieving Profiles {}", request);

        if (request != null) {
            return profileRepository.findByEmailContaining(request.getPartialEmail(), pageable);
        }
        return profileRepository.findAll(pageable);
    }

    public Profile updateProfile(long id, SaveProfileRequest request) {
        LOGGER.info("Updating profile {}", id);
        Profile profile = getProfile(id);

        BeanUtils.copyProperties(request, profile);
        return profileRepository.save(profile);
    }

    public void deleteProfile(long id) {
        LOGGER.info("Deleting profile {}", id);
        profileRepository.deleteById(id);
    }
}
