package org.fasttrackit.healthcare.persistance;

import org.fasttrackit.healthcare.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
