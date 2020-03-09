package org.fasttrackit.healthcare.persistance;

import org.fasttrackit.healthcare.domain.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
//    Page<Profile> findByEmailContaining(String partialEmail, Pageable pageable);
}
