package com.managerPass.unitTest.taskControllerTest.deleteTasksId;

import com.managerPass.entity.Enum.EPriority;
import com.managerPass.entity.Enum.ERole;
import com.managerPass.entity.TaskEntity;
import com.managerPass.unitTest.prepateTest.PrepareServiceTest;
import org.springframework.test.web.servlet.ResultActions;

public class DeleteTasksPrepareTest extends PrepareServiceTest {

    protected ResultActions deleteByIdTasks(Long param) throws Exception {
        return deleteById("/api/tasks/{idTask}" , param);
    }

    protected TaskEntity taskAdminGenerate() {
        return taskGenerate("test task", "message", EPriority.HIGH, ERole.ROLE_ADMIN,true);
    }

    @Override
    public void beforeClass() {
        userGenerate("kosto","password", ERole.ROLE_ADMIN, true);
    }
}
