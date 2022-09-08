package com.managerPass.controller;

import com.managerPass.jpa.entity.Enum.EPriority;
import com.managerPass.jpa.service.TaskRepositoryService;
import com.managerPass.payload.request.task.AddTaskRequest;
import com.managerPass.payload.request.task.UpdateTaskRequest;
import com.managerPass.payload.response.TaskResponse;
import com.managerPass.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
public class TaskController {
   
   private final TaskService taskService;
   private final TaskRepositoryService taskRepositoryService;
   
   @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
   @PreAuthorize(value = "hasRole('ADMIN')")
   public ResponseEntity<List<TaskResponse>> getTasks(@RequestParam(required = false) String name) {
      return ResponseEntity.ok(taskService.getAll(name));
   }

   @GetMapping(path = "/{idTask}", produces = MediaType.APPLICATION_JSON_VALUE)
   @PreAuthorize(value = "hasRole('USER') or hasRole('ADMIN')")
   public ResponseEntity<TaskResponse> getTasksIdTask(@PathVariable Long idTask) {
     return ResponseEntity.ok(taskService.getById(idTask));
   }

   @DeleteMapping(path = "/{idTask}")
   @PreAuthorize(value = "hasRole('USER') or hasRole('ADMIN')")
   public ResponseEntity<?> deleteTasksIdTask(@PathVariable(value = "idTask") Long idTask) {
      taskRepositoryService.deleteByIdTask(idTask);
      return ResponseEntity.ok().build();
   }

   @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   @PreAuthorize(value = "hasRole('USER') or hasRole('ADMIN')")
   public ResponseEntity<TaskResponse> postTasks(@Valid @RequestBody AddTaskRequest addTaskRequest) {
      return ResponseEntity.ok(taskService.addTask(addTaskRequest));
   }

   @PutMapping(path = "/{idTask}", consumes = MediaType.APPLICATION_JSON_VALUE,
               produces = MediaType.APPLICATION_JSON_VALUE)
   @PreAuthorize(value = "hasRole('USER') or hasRole('ADMIN')")
   public ResponseEntity<TaskResponse> putTasks(@Valid @RequestBody UpdateTaskRequest updateTaskRequest,
                                                @PathVariable Long idTask) {

      return ResponseEntity.ok(taskService.updateTask(updateTaskRequest, idTask));
   }

   @GetMapping(path = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
   @PreAuthorize(value = "hasRole('USER') or hasRole('ADMIN')")
   public ResponseEntity<List<TaskResponse>> getTasksUsers(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int sizePage,

                                                           @RequestParam(required = false) EPriority namePriority,

                                                           @RequestParam(required = false)
                                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                           LocalDateTime startDateTime,

                                                           @RequestParam(required = false)
                                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                           LocalDateTime finishDateTime) {

      Pageable pageable = PageRequest.of(page, sizePage);

      return ResponseEntity.ok().body(
              taskService.getAllByUserByNamePriorityOrDateTimeStartAndDateTimeFinish(
                      namePriority, pageable, startDateTime, finishDateTime
              )
      );
   }
}

