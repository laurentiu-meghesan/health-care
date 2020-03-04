package org.fasttrackit.healthcare;

import org.fasttrackit.healthcare.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AppointmentServiceIntegrationTest {

    @Autowired
    private AppointmentService appointmentService;

}
