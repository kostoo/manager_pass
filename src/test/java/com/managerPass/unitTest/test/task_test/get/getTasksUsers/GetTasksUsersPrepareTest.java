package com.managerPass.unitTest.test.task_test.get.getTasksUsers;

import com.managerPass.entity.Enum.EPriority;
import com.managerPass.entity.Enum.ERole;
import com.managerPass.entity.TaskEntity;
import com.managerPass.entity.UserEntity;
import com.managerPass.unitTest.prepateTest.PrepareServiceTest;

public class GetTasksUsersPrepareTest extends PrepareServiceTest {

    protected TaskEntity taskAdminGenerate(String name, String message, EPriority ePriority, UserEntity user) {

        return taskServiceProvider.taskGenerate(name, message, ePriority, user, true);
    }

    protected UserEntity userGenerate(String username, String email) {
        return userServiceProvider.userGenerate(
                username, email, ERole.ROLE_ADMIN, "nikita","nest",true
        );
    }

    @Override
    public void beforeTest() {
        userServiceProvider.userGenerate("kosto", "test@test.ru", ERole.ROLE_ADMIN, "nikita",
                "lastname", true);
    }
}
