package org.fasttrackit.healthcare.peristance;

import org.fasttrackit.healthcare.domain.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {

}
