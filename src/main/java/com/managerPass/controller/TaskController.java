package com.managerPass.controller;

import com.managerPass.entity.TaskEntity;
import com.managerPass.service.TaskEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TaskController {

   private final TaskEntityService taskService;

   @GetMapping(path = "/tasks")
   @PreAuthorize(value = "hasRole('ADMIN')")
   public ResponseEntity<List<TaskEntity>> getUserGetAll() {
      return ResponseEntity.ok(taskService.findAll());
   }

   @GetMapping(path = "/tasks/name/{name}",
               consumes = MediaType.APPLICATION_JSON_VALUE,
               produces = MediaType.APPLICATION_JSON_VALUE)
   @PreAuthorize(value = "hasRole('ADMIN')")
   public ResponseEntity<ArrayList<TaskEntity>> getAllByName(@Param("name") String name){
      ArrayList<TaskEntity> listTaskByName = taskService.findAllByName(name);
      return ResponseEntity.ok().body(listTaskByName);
   }

   @GetMapping(path = "/task/{id}")
   @PreAuthorize(value = "hasRole('USER')  or hasRole('ADMIN')")
   public ResponseEntity<TaskEntity> findTaskEntityByIdTask(@PathVariable(value = "id") Long id){
     TaskEntity taskEntity = taskService.findTaskEntityByIdTask(id);
     return ResponseEntity.ok(taskEntity);
   }

   @DeleteMapping(path = "/task/{id_task}")
   @PreAuthorize(value = "hasRole('USER')  or hasRole('ADMIN')")
   public ResponseEntity<TaskEntity> deleteTaskEntityByIdTask(@PathVariable(value = "id_task") Long id){
      taskService.deleteTaskEntityByIdTask(id);
      return ResponseEntity.ok().build();
   }

   @PostMapping(path = "/task",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
   @PreAuthorize(value = "hasRole('USER')  or hasRole('ADMIN')")
   public ResponseEntity<TaskEntity> addTask(@RequestBody TaskEntity taskEntity){
      TaskEntity entity = taskService.addTask(taskEntity);
      return ResponseEntity.ok(entity);
   }

   @PutMapping(path = "/task")
   @PreAuthorize(value = "hasRole('USER')  or hasRole('ADMIN')")
   public ResponseEntity<TaskEntity> updateTask(@RequestBody TaskEntity taskEntity){
      return ResponseEntity.ok(taskService.updateTask(taskEntity));
   }

   @GetMapping(path = "/tasks/byUser")
   @PreAuthorize(value = " hasRole('USER')")
   public ArrayList<TaskEntity> findAllByUserEntity(){
      return taskService.findAllByUserEntity();
   }

   @GetMapping(path = "/tasks/byUser/byPriority/{id_priority}")
   @PreAuthorize(value = " hasRole('USER')")
   public ArrayList<TaskEntity> findAllByPriority(@PathVariable(value = "id_priority") Long idPriority){
      return taskService.findAllByUserAndPriority(idPriority);
   }
}
