package com.managerPass;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.managerPass.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class PrepareServiceTest {

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected MockMvc mvc;

    @Autowired
    protected  PriorityEntityRepository priorityEntityRepository;

    @Autowired
    protected  RoleRepository roleRepository;

    @Autowired
    protected TaskEntityRepository taskEntityRepository;

    @Autowired
    protected UserEntityRepository userEntityRepository;

    @Autowired
    protected ValidateTokenRepository validateTokenRepository;

    @AfterEach
    public void deleteAllAfterRegistration() {
        validateTokenRepository.deleteAllInBatch();
        taskEntityRepository.deleteAllInBatch();
        userEntityRepository.deleteAllInBatch();
        priorityEntityRepository.deleteAllInBatch();
        roleRepository.deleteAllInBatch();


    }
}
