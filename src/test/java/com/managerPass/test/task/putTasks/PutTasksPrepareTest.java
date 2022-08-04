package com.managerPass.test.task.putTasks;

import com.managerPass.entity.Enum.EPriority;
import com.managerPass.entity.Enum.ERole;
import com.managerPass.entity.TaskEntity;
import com.managerPass.payload.response.TaskResponse;
import com.managerPass.prepateTest.PrepareServiceTest;
import com.managerPass.util.TaskEntityConverter;
import org.springframework.test.web.servlet.ResultActions;

public class PutTasksPrepareTest extends PrepareServiceTest {

    protected TaskResponse taskUpdateGenerate(String nameTask, String message, EPriority ePriority, ERole eRole ) {
        return TaskEntityConverter.taskResponseGenerate(
                taskServiceProvider.taskGenerate(nameTask, message, ePriority, eRole, false)
        );
    }

    protected ResultActions sendPutTaskResponseAndGetResultActions(Object updateObject, Long idTask) throws Exception {
        return  sendPutAndGetResultActions(updateObject, idTask);
    }

    protected TaskEntity taskAddGenerate() {
        return taskServiceProvider.taskGenerate(
                "test task", "message", EPriority.HIGH, ERole.ROLE_ADMIN, true
        );
    }
    @Override
    public void beforeTest() {
        userServiceProvider.userGenerate(
                "kosto", "password", ERole.ROLE_ADMIN,"nik", "nest", true
        );
    }
}
