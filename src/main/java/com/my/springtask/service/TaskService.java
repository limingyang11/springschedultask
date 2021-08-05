package com.my.springtask.service;

import com.my.springtask.dto.ResultDTO;
import com.my.springtask.dto.TaskJobDTO;
import com.my.springtask.task.SchedulTask;
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
    public TaskService(SchedulTask schedulTask) {
        this.schedulTask = schedulTask;
    }

    /**
     * 新增或修改
     *
     * @param taskJobDTO
     * @return
     */
    public ResultDTO addTask(TaskJobDTO taskJobDTO) {
        ResultDTO resultDTO = new ResultDTO<>(ResultUtil.OK, "success", taskJobDTO);
        resultDTO = schedulTask.addTask(taskJobDTO);
        if (map.get(taskJobDTO.getId()) != null) {
            map.put(taskJobDTO.getId(), taskJobDTO);
        }
        return resultDTO;
    }

    /**
     * 删除或者暂停
     *
     * @param id
     * @return
     */
    public ResultDTO deleteTask(long id) {
        if (map.get(id) == null) {
            return new ResultDTO(ResultUtil.RESOURCE_IS_NOT_EXIST, ResultUtil.getMessage(ResultUtil.RESOURCE_IS_NOT_EXIST), id);
        }

        return schedulTask.removeTask(id);
    }

    /**
     * 恢复
     *
     * @param id
     * @return
     */
    public ResultDTO resumeTask(long id) {
        TaskJobDTO taskJobDTO = map.get(id);
        if (taskJobDTO == null) {
            return new ResultDTO(ResultUtil.RESOURCE_IS_NOT_EXIST, ResultUtil.getMessage(ResultUtil.RESOURCE_IS_NOT_EXIST), id);
        }

        return schedulTask.addTask(taskJobDTO);
    }
}
