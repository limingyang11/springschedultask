package com.my.springtask.service;

import com.my.springtask.dto.TaskJobDTO;
import com.my.springtask.task.SchedulTask;
import com.my.springtask.utils.ResultException;
import com.my.springtask.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TaskService {
    private final SchedulTask schedulTask;
    private static Map<Long, TaskJobDTO> map = new HashMap<>();

    @Autowired
    public TaskService (SchedulTask schedulTask) {
        this.schedulTask = schedulTask;
    }

    /**
     * 新增或修改
     * @param taskJobDTO
     * @return
     */
    public String addTask(TaskJobDTO taskJobDTO) {
        schedulTask.addTask(taskJobDTO);
        if (map.get(taskJobDTO.getId()) != null) {
            map.put(taskJobDTO.getId(), taskJobDTO);
        }
        return "success";
    }

    /**
     * 删除或者暂停
     * @param id
     * @return
     */
    public String deleteTask(long id) {
        if (map.get(id) == null) {
            throw new ResultException(ResultUtil.RESOURCE_IS_NOT_EXIST, ResultUtil.getMessage(ResultUtil.RESOURCE_IS_NOT_EXIST));
        }
        schedulTask.removeTask(id);
        return "success";
    }

    /**
     * 恢复
     * @param id
     * @return
     */
    public String resumeTask(long id) {
        TaskJobDTO taskJobDTO = map.get(id);
        if (taskJobDTO == null) {
            throw new ResultException(ResultUtil.RESOURCE_IS_NOT_EXIST, ResultUtil.getMessage(ResultUtil.RESOURCE_IS_NOT_EXIST));
        }
        schedulTask.addTask(taskJobDTO);
        return "success";
    }
}
