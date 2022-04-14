package com.managerPass.controller;

import com.managerPass.entity.TaskEntity;
import com.managerPass.service.TaskEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
public class TaskController {

   private final TaskEntityService taskService;

   @GetMapping
   @PreAuthorize(value = "hasRole('ADMIN')")
   public ResponseEntity<List<TaskEntity>> getTasks() {
      return ResponseEntity.ok(taskService.getAll());
   }

   @GetMapping(path = "/name/{name}",
               consumes = MediaType.APPLICATION_JSON_VALUE,
               produces = MediaType.APPLICATION_JSON_VALUE)
   @PreAuthorize(value = "hasRole('ADMIN')")
   public ResponseEntity<ArrayList<TaskEntity>> getTasksName(@PathVariable(value = "name") String name) {
      ArrayList<TaskEntity> listTaskByName = taskService.getAllByName(name);
      return ResponseEntity.ok().body(listTaskByName);
   }

   @GetMapping(path = "/{idTask}")
   @PreAuthorize(value = "hasRole('USER')  or hasRole('ADMIN')")
   public ResponseEntity<TaskEntity> getTasksIdTask(@PathVariable(value = "idTask") Long idTask) {
     return ResponseEntity.ok(taskService.getByIdTask(idTask));
   }

   @DeleteMapping(path = "/{idTask}")
   @PreAuthorize(value = "hasRole('USER')  or hasRole('ADMIN')")
   public ResponseEntity<TaskEntity> deleteTasksIdTask(@PathVariable(value = "idTask") Long idTask) {
      taskService.deleteByIdTask(idTask);
      return ResponseEntity.ok().build();
   }

   @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
   @PreAuthorize(value = "hasRole('USER')  or hasRole('ADMIN')")
   public ResponseEntity<TaskEntity> postTasks(@RequestBody TaskEntity taskEntity) {
      return ResponseEntity.ok(taskService.postTask(taskEntity));
   }

   @PutMapping
   @PreAuthorize(value = "hasRole('USER')  or hasRole('ADMIN')")
   public ResponseEntity<TaskEntity> putTasks(@RequestBody TaskEntity taskEntity) {
      return ResponseEntity.ok(taskService.putTask(taskEntity));
   }

   @GetMapping(path = "/user")
   @PreAuthorize(value = "hasRole('USER')  or hasRole('ADMIN')")
   public ResponseEntity<ArrayList<TaskEntity>> getTasksUser() {
      return ResponseEntity.ok().body(taskService.getAllByAuthUser());
   }

   @GetMapping(path = "/user/priority/{idPriority}")
   @PreAuthorize(value = "hasRole('USER')  or hasRole('ADMIN')")
   public ResponseEntity<ArrayList<TaskEntity>> getTasksUserPriorityIdPriority(@PathVariable (value = "idPriority")
                                                                               Long idPriority) {
      return ResponseEntity.ok().body(taskService.getAllByAuthUserAndPriority(idPriority));
   }

   @GetMapping(path = "/user/{page}")
   @PreAuthorize(value = "hasRole('USER')  or hasRole('ADMIN')")
   public ResponseEntity<List<TaskEntity>> getTasksUserPage(@PathVariable (value = "page") int page) {
      int sizePage = 10;
      Pageable pageable = PageRequest.of(page,sizePage);

      return ResponseEntity.ok().body(taskService.findAllByUserEntityPage(pageable).getContent());
   }

   @GetMapping(path = "/user/{page}/{dateTimeStart}/{dateTimeFinish}")
   @PreAuthorize(value = "hasRole('USER')  or hasRole('ADMIN')")
   public ResponseEntity<List<TaskEntity>> getTasksUserIdAndDateAfterBefore(@PathVariable(value = "page") int page,
                                                                            @PathVariable(value = "dateTimeStart")
                                                                            @DateTimeFormat(
                                                                                  iso = DateTimeFormat.ISO.DATE_TIME
                                                                             )
                                                                            LocalDateTime startDateTime,
                                                                            @PathVariable(value = "dateTimeFinish")
                                                                            @DateTimeFormat(
                                                                                  iso = DateTimeFormat.ISO.DATE_TIME
                                                                            )
                                                                            LocalDateTime dateTimeFinish) {
      int sizePage = 10;
      Pageable pageable = PageRequest.of(page,sizePage);

      return ResponseEntity.ok().body(taskService.findAllByUserIdDateTimeStartAfterAndDateTimeFinishBefore(
                                   startDateTime, dateTimeFinish, pageable)
             .getContent()
      );
   }
}

