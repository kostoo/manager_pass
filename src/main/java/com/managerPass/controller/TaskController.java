package com.managerPass.controller;


import com.managerPass.entity.TaskEntity;
import com.managerPass.entity.UserEntity;
import com.managerPass.service.TaskEntityService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TaskController {

   private final TaskEntityService taskService;

   @GetMapping(path = "/getAllTask",
               consumes = MediaType.APPLICATION_JSON_VALUE,
               produces = MediaType.APPLICATION_JSON_VALUE)
   @PreAuthorize(value = "hasRole('ADMIN')")
   public ResponseEntity<List<TaskEntity>> getUserGetAll() {

      return ResponseEntity.ok(taskService.findAll());
   }

   @GetMapping(path = "/getAllTaskByName/{name}",
               consumes = MediaType.APPLICATION_JSON_VALUE,
               produces = MediaType.APPLICATION_JSON_VALUE)
   @PreAuthorize(value = "hasRole('ADMIN')")
   public ResponseEntity<ArrayList<TaskEntity>> findAllByName(@Param("name") String name){
       ArrayList<TaskEntity> listTaskByName = taskService.findAllByName(name);

      return ResponseEntity.ok().body(listTaskByName);
   }

   @GetMapping(path = "/getTask/{id}")
   @PreAuthorize(value = "hasRole('USER')  or hasRole('ADMIN')")
   public ResponseEntity<TaskEntity> findTaskEntityByIdTask(@PathVariable(value = "id") Long id){
     TaskEntity taskEntity = taskService.findTaskEntityByIdTask(id);

      return ResponseEntity.ok(taskEntity);
   }

   @DeleteMapping(path = "/deleteTask/{id}")
   @PreAuthorize(value = "hasRole('USER')  or hasRole('ADMIN')")
   public ResponseEntity deleteTaskEntityByIdTask(@PathVariable(value = "id") Long id){
      taskService.deleteTaskEntityByIdTask(id);

      return new ResponseEntity(HttpStatus.OK);
   }

   @PostMapping(path = "/addTask",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
   @PreAuthorize(value = "hasRole('USER')  or hasRole('ADMIN')")
   public ResponseEntity addTask(@RequestBody TaskEntity taskEntity){
      TaskEntity entity = taskService.addTask(taskEntity);

      return ResponseEntity.ok(entity);
   }

   @PutMapping(path = "/updateTask")
   @PreAuthorize(value = "hasRole('USER')  or hasRole('ADMIN')")
   public ResponseEntity updateTask(@RequestBody TaskEntity taskEntity){
      TaskEntity entity = taskService.updateTask(taskEntity);

      return ResponseEntity.ok(entity);
   }
}
