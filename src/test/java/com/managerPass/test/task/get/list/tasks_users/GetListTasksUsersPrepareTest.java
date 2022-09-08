package com.managerPass.test.task.get.list.tasks_users;

import com.managerPass.jpa.entity.Enum.EPriority;
import com.managerPass.jpa.entity.Enum.ERole;
import com.managerPass.jpa.entity.TaskEntity;
import com.managerPass.jpa.entity.UserEntity;
import com.managerPass.prepateTest.PrepareServiceTest;

public class GetListTasksUsersPrepareTest extends PrepareServiceTest {

    protected TaskEntity taskAdminGenerate(String name, String message, EPriority ePriority, UserEntity user) {

        return taskProvider.taskGenerateDb(name, message, ePriority, user);
    }

    protected UserEntity userGenerate(String username, String email) {
        return userProvider.userGenerateDb(username, email, ERole.ROLE_ADMIN, "nikita", "nest");
    }

    @Override
    public void beforeTest() {
        userProvider.userGenerateDb("kosto", "test@test.ru", ERole.ROLE_ADMIN, "nikita", "lastname");
    }
}
