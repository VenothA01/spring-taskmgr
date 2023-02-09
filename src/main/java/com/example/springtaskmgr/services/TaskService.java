package com.example.springtaskmgr.services;

import com.example.springtaskmgr.dto.ErrorResponse;
import com.example.springtaskmgr.entities.Task;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
@Service
public class TaskService {


    public static class TaskNotFoundException extends RuntimeException{

        public TaskNotFoundException(Integer id){
            super("task with id"+id+"not found");
        }
    }

    private final List<Task> taskList;
    private AtomicInteger taskId = new AtomicInteger(1);

    public TaskService(){
        taskList = new ArrayList<>();
        taskList.add(new Task(taskId.getAndIncrement(),"Task 1","Task 1 description","2023-08-09" ));
        taskList.add(new Task(taskId.getAndIncrement(),"Task 2","Task 2 description","2023-08-09" ));
        taskList.add(new Task(taskId.getAndIncrement(),"Task 3","Task 3 description","2023-08-09" ));
    }

    public List<Task> getTaks(){
        return taskList;
    }

    public Task createTask(String title,String description,String date){
        Task task = new Task(taskId.getAndIncrement(),title,description,date);
        taskList.add(task);
        return task;
    }


    public Task getTaskById(Integer taskId){
        return taskList.stream().filter(x->x.getId().equals(taskId)).findFirst().orElseThrow(()->new TaskNotFoundException(taskId));
    }

    public Task updateTask(Integer id,String title,String description,String date){

        Task task = getTaskById(id);
        if(title!=null) task.setTitle(title);
        if(description!=null) task.setDescription(title);
        if(date!=null) task.setDueDate(title);
        return task;
    }

    public Task deleteTask(Integer taskId){
        Task task = getTaskById(taskId);
        taskList.remove(task);
        return task ;
    }




}
