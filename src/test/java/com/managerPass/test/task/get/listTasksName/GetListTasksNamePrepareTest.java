package com.managerPass.test.task.get.listTasksName;

import com.managerPass.jpa.entity.Enum.EPriority;
import com.managerPass.jpa.entity.Enum.ERole;
import com.managerPass.jpa.entity.TaskEntity;
import com.managerPass.prepateTest.PrepareServiceTest;
import org.springframework.test.web.servlet.ResultActions;

public class GetListTasksNamePrepareTest extends PrepareServiceTest {

    protected TaskEntity taskAdminGenerate() {
        return taskProvider.taskGenerate(
                "getTasksName", "taskNameMessage", EPriority.HIGH, ERole.ROLE_ADMIN, true
        );
    }

    @Override
    public void beforeTest() {
        userProvider.userGenerate(
         "kosto", "test@test.ru", ERole.ROLE_ADMIN, "nikita", "lastname", true
        );
    }

    protected ResultActions getActionsTasksName(Object... uriVars) throws Exception {
        return getActionResult("/api/tasks?name={name}", uriVars);
    }
}
