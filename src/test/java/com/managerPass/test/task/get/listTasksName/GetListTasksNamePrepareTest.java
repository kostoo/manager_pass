package com.managerPass.test.task.get.listTasksName;

import com.managerPass.entity.Enum.EPriority;
import com.managerPass.entity.Enum.ERole;
import com.managerPass.entity.TaskEntity;
import com.managerPass.prepateTest.PrepareServiceTest;
import org.springframework.test.web.servlet.ResultActions;

public class GetListTasksNamePrepareTest extends PrepareServiceTest {

    protected TaskEntity taskAdminGenerate() {
        return taskServiceProvider.taskGenerate(
                "getTasksName", "taskNameMessage", EPriority.HIGH, ERole.ROLE_ADMIN, true
        );
    }

    @Override
    public void beforeTest() {
        userServiceProvider.userGenerate(
         "kosto", "test@test.ru", ERole.ROLE_ADMIN, "nikita", "lastname", true
        );
    }

    protected ResultActions getActionsTasksName(Object... uriVars) throws Exception {
        return getActionResult("/api/tasks?name={name}", uriVars);
    }
}
