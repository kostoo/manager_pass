package com.managerPass.test.task.put;

import com.managerPass.jpa.entity.Enum.EPriority;
import com.managerPass.jpa.entity.Enum.ERole;
import com.managerPass.jpa.entity.TaskEntity;
import com.managerPass.payload.request.task.TaskRequest;
import com.managerPass.prepateTest.PrepareServiceTest;
import org.springframework.test.web.servlet.ResultActions;

public class PutTasksPrepareTest extends PrepareServiceTest {

    protected TaskRequest taskUpdateGenerate(String nameTask, ERole eRole) {
        return taskProvider.taskRequestGenerate(
                taskProvider.taskGenerate(nameTask, "update message", EPriority.LOW, eRole)
        );
    }

    protected ResultActions sendPutTaskResponseAndGetResultActions(Object updateObject, Long idTask) {
        return  sendPutAndGetResultActions(updateObject, idTask);
    }

    protected TaskEntity taskAddGenerate() {
        return taskProvider.taskGenerate("test task", "message", EPriority.HIGH, ERole.ROLE_ADMIN);
    }
    @Override
    public void beforeTest() {
        userProvider.userGenerateDb("kosto", "password", ERole.ROLE_ADMIN, "nik", "nest");
    }
}
