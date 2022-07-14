package com.managerPass.unitTest.taskControllerTest.postTasks;

import com.managerPass.entity.Enum.EPriority;
import com.managerPass.entity.Enum.ERole;
import com.managerPass.entity.TaskEntity;
import com.managerPass.unitTest.prepateTest.PrepareServiceTest;
import org.springframework.test.web.servlet.ResultActions;

public class PostTasksPrepareTest extends PrepareServiceTest {

    protected TaskEntity taskAdminDbFalseGenerate() {
        return taskGenerate("test task", "message", EPriority.HIGH, ERole.ROLE_ADMIN,false);
    }

    @Override
    public void beforeClass() {
        userGenerate("kosto","test@test.ru", ERole.ROLE_ADMIN, true);
    }

    protected ResultActions sendPostTasksAndGetResultActions(Object addObject) throws Exception {
        return sendPostAndGetResultActions("/api/tasks", addObject);
    }
}
