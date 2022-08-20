package com.managerPass.test.task.post;

import com.managerPass.entity.Enum.EPriority;
import com.managerPass.entity.Enum.ERole;
import com.managerPass.payload.response.TaskResponse;
import com.managerPass.prepateTest.PrepareServiceTest;
import com.managerPass.util.TaskEntityConverter;
import org.springframework.test.web.servlet.ResultActions;

public class PostTasksPrepareTest extends PrepareServiceTest {

    protected TaskResponse taskDbFalseGenerate(String nameTask, String message, EPriority ePriority, ERole eRole ) {
        return TaskEntityConverter.taskResponseGenerate(
                taskServiceProvider.taskGenerate(nameTask, message, ePriority, eRole, false)
        );
    }

    @Override
    public void beforeTest() {
        userServiceProvider.userGenerate(
             "kosto", "test@test.ru", ERole.ROLE_ADMIN, "nikita", "lastname", true
        );
    }

    protected ResultActions sendPostTasksAndGetResultActions(Object addObject) throws Exception {
        return sendPostAndGetResultActions("/api/tasks", addObject);
    }
}
