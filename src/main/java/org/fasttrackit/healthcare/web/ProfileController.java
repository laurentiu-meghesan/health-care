package org.fasttrackit.healthcare.web;

import org.fasttrackit.healthcare.domain.Profile;
import org.fasttrackit.healthcare.service.ProfileService;
import org.fasttrackit.healthcare.transfer.profile.GetProfilesRequest;
import org.fasttrackit.healthcare.transfer.profile.SaveProfileRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/profiles")
public class ProfileController {

    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping
    public ResponseEntity<Profile> createProfile(@Valid @RequestBody SaveProfileRequest request) {
        Profile profile = profileService.createProfile(request);
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profile> getProfile(@PathVariable long id) {
        Profile profile = profileService.getProfile(id);
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<Profile>> getProfiles(GetProfilesRequest request, Pageable pageable) {
        Page<Profile> profiles = profileService.getProfiles(request, pageable);
        return new ResponseEntity<>(profiles, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Profile> updateProfile(@PathVariable long id, @Valid @RequestBody SaveProfileRequest request) {
        Profile profile = profileService.updateProfile(id, request);
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable long id) {
        profileService.deleteProfile(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
