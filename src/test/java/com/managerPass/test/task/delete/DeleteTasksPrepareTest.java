package com.managerPass.test.task.delete;

import com.managerPass.jpa.entity.Enum.EPriority;
import com.managerPass.jpa.entity.Enum.ERole;
import com.managerPass.jpa.entity.TaskEntity;
import com.managerPass.prepateTest.PrepareServiceTest;
import org.springframework.test.web.servlet.ResultActions;

public class DeleteTasksPrepareTest extends PrepareServiceTest {

    protected ResultActions deleteByIdTasks(Long param) {
        return deleteById("/api/tasks/{idTask}", param);
    }

    protected TaskEntity taskAdminGenerate() {
        return taskProvider.taskGenerateDb("test task", "message", EPriority.HIGH, ERole.ROLE_ADMIN);
    }

    @Override
    public void beforeTest() {
        userProvider.userGenerateDb("kosto", "test@test.ru", ERole.ROLE_ADMIN, "nikita", "lastname");
    }
}
