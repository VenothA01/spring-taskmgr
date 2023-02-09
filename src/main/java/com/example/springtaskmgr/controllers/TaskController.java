package com.example.springtaskmgr.controllers;

import com.example.springtaskmgr.dto.ErrorResponse;
import com.example.springtaskmgr.entities.Task;
import com.example.springtaskmgr.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/tasks")
    public ResponseEntity<List<Task>> getAllTask(){
        return ResponseEntity.ok(taskService.getTaks());
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Integer id){
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @PostMapping("/tasks")
    public ResponseEntity<Task> createTask(@RequestBody Task task){
        Task newTask = taskService.createTask(task.getTitle(),task.getDescription(),task.getDueDate());
        return ResponseEntity.created(URI.create("/tasks/"+newTask.getId())).body(newTask);
    }

    @PatchMapping("tasks/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Integer id,@RequestBody Task task){
        Task updatedTask = taskService.updateTask(id,task.getTitle(),task.getDescription(),task.getDueDate());
        return ResponseEntity.accepted().body(updatedTask);
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Task> deleteTaskById(@PathVariable Integer id){
        Task task = taskService.deleteTask(id);
        return ResponseEntity.accepted().body(task);
    }

    @ExceptionHandler(TaskService.TaskNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleErrors(TaskService.TaskNotFoundException e){
        return new ResponseEntity<>(
                new ErrorResponse(e.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }





}
