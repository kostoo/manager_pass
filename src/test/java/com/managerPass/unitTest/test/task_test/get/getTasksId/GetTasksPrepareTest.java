package com.managerPass.unitTest.test.task_test.get.getTasksId;

import com.managerPass.entity.Enum.EPriority;
import com.managerPass.entity.Enum.ERole;
import com.managerPass.entity.TaskEntity;
import com.managerPass.unitTest.prepateTest.PrepareServiceTest;
import org.springframework.test.web.servlet.ResultActions;

public class GetTasksPrepareTest extends PrepareServiceTest {

    protected ResultActions getTasksActionResult(Long uriVars) throws Exception {
       return getActionResult("/api/tasks/{idTask}", uriVars);
    }

    protected TaskEntity taskAdminGenerate() {
        return taskServiceProvider.taskGenerate("test task", "message", EPriority.HIGH, ERole.ROLE_ADMIN, true);
    }

    @Override
    public void beforeTest() {
        userServiceProvider.userGenerate("kosto", "test@test.ru", ERole.ROLE_ADMIN, "nikita",
                "lastname", true);
    }
}
