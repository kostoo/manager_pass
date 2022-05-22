package com.managerPass.controller;

import com.managerPass.entity.TaskEntity;
import com.managerPass.payload.request.TaskRequest;
import com.managerPass.service.TaskEntityService;
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

   private final TaskEntityService taskService;

   @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
   @PreAuthorize(value = "hasRole('ADMIN')")
   public ResponseEntity<List<TaskEntity>> getTasks() {
      return ResponseEntity.ok(taskService.getAll());
   }

   @GetMapping(path = "/name/{name}",
               produces = MediaType.APPLICATION_JSON_VALUE)
   @PreAuthorize(value = "hasRole('ADMIN')")
   public ResponseEntity<List<TaskEntity>> getTasksName(@PathVariable String name) {
      return ResponseEntity.ok().body(taskService.getAllByName(name));
   }

   @GetMapping(path = "/{idTask}", produces = MediaType.APPLICATION_JSON_VALUE)
   @PreAuthorize(value = "hasRole('USER') or hasRole('ADMIN')")
   public ResponseEntity<TaskEntity> getTasksIdTask(@PathVariable Long idTask) {
     return ResponseEntity.ok(taskService.getByIdTask(idTask));
   }

   @DeleteMapping(path = "/{idTask}")
   @PreAuthorize(value = "hasRole('USER') or hasRole('ADMIN')")
   public ResponseEntity<TaskEntity> deleteTasksIdTask(@PathVariable(value = "idTask") Long idTask) {
      taskService.deleteByIdTask(idTask);
      return ResponseEntity.ok().build();
   }

   @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
   @PreAuthorize(value = "hasRole('USER') or hasRole('ADMIN')")
   public ResponseEntity<TaskEntity> postTasks(@Valid @RequestBody TaskRequest taskRequest) {
      return ResponseEntity.ok(taskService.addTask(taskRequest));
   }

   @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
               produces = MediaType.APPLICATION_JSON_VALUE)
   @PreAuthorize(value = "hasRole('USER') or hasRole('ADMIN')")
   public ResponseEntity<TaskEntity> putTasks(@Valid @RequestBody TaskEntity taskEntity) {
      return ResponseEntity.ok(taskService.updateTask(taskEntity));
   }

   @GetMapping(path = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
   @PreAuthorize(value = "hasRole('USER') or hasRole('ADMIN')")
   public ResponseEntity<List<TaskEntity>> getTasksUsers(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int sizePage,
                                                         @RequestParam(required = false) Long idPriority,

                                                         @RequestParam(required = false) @DateTimeFormat(
                                                                iso = DateTimeFormat.ISO.DATE_TIME
                                                         ) LocalDateTime startDateTime,

                                                         @RequestParam(required = false) @DateTimeFormat(
                                                                iso = DateTimeFormat.ISO.DATE_TIME
                                                         ) LocalDateTime dateTimeFinish) {

      Pageable pageable = PageRequest.of(page, sizePage);

      return ResponseEntity.ok().body(taskService.getAllByAuthUserWithParam(
              idPriority, pageable, startDateTime, dateTimeFinish
      ));
   }
}

