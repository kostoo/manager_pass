package com.managerPass.test.task.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.managerPass.jpa.entity.Enum.EPriority;
import com.managerPass.jpa.entity.Enum.ERole;
import com.managerPass.payload.request.task.TaskRequest;
import com.managerPass.payload.response.TaskResponse;
import com.managerPass.prepateTest.PrepareServiceTest;
import lombok.SneakyThrows;
import org.springframework.test.web.servlet.ResultActions;

public class PostTasksPrepareTest extends PrepareServiceTest {

    protected TaskRequest taskDbFalseGenerate(String nameTask, String message, EPriority ePriority, ERole eRole) {
        return taskProvider.taskRequestGenerate(
                         taskProvider.taskGenerate(nameTask, message, ePriority, eRole, false)
        );
    }

    @Override
    public void beforeTest() {
        userProvider.userGenerate(
             "kosto", "test@test.ru", ERole.ROLE_ADMIN, "nikita", "lastname", true
        );
    }

    protected ResultActions sendPostTasksAndGetResultActions(Object addObject) {
        return sendPostAndGetResultActions("/api/tasks", addObject);
    }

    @SneakyThrows
    protected TaskResponse convertResultActionsToTaskResponse(ResultActions resultActions) {
        return new ObjectMapper().readValue(
                resultActions.andReturn().getResponse().getContentAsString(), TaskResponse.class
        );
    }
}
