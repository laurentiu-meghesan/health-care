package org.fasttrackit.healthcare.persistance;

import org.fasttrackit.healthcare.domain.Chat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query(value = "SELECT * FROM chat WHERE patient_id=? ORDER BY message_date, message_received_date",
            nativeQuery = true)
    Page<Chat> findByPatientIdOrderByMessageDateAndMessageReceivedDate(Long patientId, Pageable pageable);

    @Query(value = "SELECT * FROM chat WHERE doctor_id=? ORDER BY message_date, message_received_date",
            nativeQuery = true)
    Page<Chat> findByDoctorIdOrderByMessageDateAndMessageReceivedDate(Long doctorId, Pageable pageable);
}
