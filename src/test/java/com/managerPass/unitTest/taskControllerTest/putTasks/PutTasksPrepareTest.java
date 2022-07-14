package com.managerPass.unitTest.taskControllerTest.putTasks;

import com.managerPass.entity.Enum.EPriority;
import com.managerPass.entity.Enum.ERole;
import com.managerPass.entity.TaskEntity;
import com.managerPass.unitTest.prepateTest.PrepareServiceTest;

public class PutTasksPrepareTest extends PrepareServiceTest {

    protected TaskEntity taskAdminGenerate() {
        return taskGenerate("test task", "message", EPriority.HIGH, ERole.ROLE_ADMIN,true);
    }

    @Override
    public void beforeClass() {
        userGenerate("kosto","password", ERole.ROLE_ADMIN, true);
    }
}
