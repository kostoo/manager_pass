package com.managerPass.test.task.get.listTasksUsers;

import com.managerPass.jpa.entity.Enum.EPriority;
import com.managerPass.jpa.entity.Enum.ERole;
import com.managerPass.jpa.entity.TaskEntity;
import com.managerPass.jpa.entity.UserEntity;
import com.managerPass.prepateTest.PrepareServiceTest;

public class GetListTasksUsersPrepareTest extends PrepareServiceTest {

    protected TaskEntity taskAdminGenerate(String name, String message, EPriority ePriority, UserEntity user) {

        return taskProvider.taskGenerate(name, message, ePriority, user, true);
    }

    protected UserEntity userGenerate(String username, String email) {
        return userProvider.userGenerate(
                username, email, ERole.ROLE_ADMIN, "nikita", "nest", true
        );
    }

    @Override
    public void beforeTest() {
        userProvider.userGenerate(
              "kosto", "test@test.ru", ERole.ROLE_ADMIN, "nikita", "lastname", true
        );
    }
}
