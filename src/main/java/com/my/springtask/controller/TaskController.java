package com.my.springtask.controller;

import com.my.springtask.dto.ResultDTO;
import com.my.springtask.dto.TaskJobDTO;
import com.my.springtask.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/1/task")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController (TaskService taskService) {
        this.taskService = taskService;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public ResultDTO addTask(@RequestBody TaskJobDTO taskJobDTO) {
        return taskService.addTask(taskJobDTO);
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public ResultDTO modifyTask(@RequestBody TaskJobDTO taskJobDTO) {
        return taskService.addTask(taskJobDTO);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public ResultDTO deleteTask(@RequestParam(value = "id", required = true) long id) {
        return taskService.deleteTask(id);
    }

    @RequestMapping(value = "/resume", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public ResultDTO resumeTask(@RequestParam(value = "id", required = true) long id) {
        return taskService.resumeTask(id);
    }
}
