package com.managerPass.unitTest.taskControllerTest.getTasksName;

import com.managerPass.entity.Enum.EPriority;
import com.managerPass.entity.Enum.ERole;
import com.managerPass.entity.TaskEntity;
import com.managerPass.unitTest.prepateTest.PrepareServiceTest;
import org.springframework.test.web.servlet.ResultActions;

public class GetTasksNamePrepareTest extends PrepareServiceTest {

    protected TaskEntity taskAdminGenerate() {
        return taskGenerate("test task", "message", EPriority.HIGH, ERole.ROLE_ADMIN,true);
    }

    @Override
    public void beforeClass() {
        userGenerate("kosto","test@test.ru", ERole.ROLE_ADMIN, true);
    }

    protected ResultActions getActionsTasksName(Object... uriVars) throws Exception {
        return getActionResult("/api/tasks?name={name}", uriVars);
    }
}
