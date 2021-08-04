package com.my.springtask.task;

import com.my.springtask.dto.TaskJobDTO;
import com.my.springtask.utils.ResultException;
import com.my.springtask.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@EnableScheduling
public class SchedulTask implements SchedulingConfigurer {
    private static final Logger LOGGER = LoggerFactory.getLogger(SchedulTask.class);
    private final Map<Long, ScheduledTask> scheduledTaskMap = new HashMap<>();
    private ScheduledTaskRegistrar scheduledTaskRegistrar;

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        this.scheduledTaskRegistrar = scheduledTaskRegistrar;
    }

    /**
     * 添加定时任务
     * @param taskJobDTO
     */
    public void addTask(TaskJobDTO taskJobDTO) {
        String cron = getCron(taskJobDTO.getStartTime());
        if (StringUtils.isEmpty(cron)) {
            throw new ResultException(ResultUtil.CRON_IS_NOT_EMPTY, ResultUtil.getMessage(ResultUtil.CRON_IS_NOT_EMPTY));
        }

        try {
            removeTask(taskJobDTO.getId());

            CronTask task = new CronTask(() -> executeJob(taskJobDTO), cron);
            ScheduledTask scheduledTask = scheduledTaskRegistrar.scheduleCronTask(task);
            scheduledTaskMap.put(taskJobDTO.getId(), scheduledTask);
            LOGGER.info("新增定时任务成功：{}", taskJobDTO.getId());
        }catch (Exception e) {
            LOGGER.error("新增定时任务失败：{}", taskJobDTO.getId());
            throw new ResultException(ResultUtil.ADD_NEW_TASK_FAILED, ResultUtil.getMessage(ResultUtil.ADD_NEW_TASK_FAILED));
        }
    }

    /**
     * 删除定时任务
     * @param id
     */
    public void removeTask(long id) {
        ScheduledTask scheduledTask = scheduledTaskMap.get(id);
        if (scheduledTask != null) {
            scheduledTask.cancel();
            LOGGER.info("删除定时任务成功：{}" , id);
        }
    }

    private void executeJob(TaskJobDTO taskJobDTO) {
        System.out.println(new Date() + "--------" + taskJobDTO.toString());
        /*这里写的是需要执行的任务   接口参数可以自己定义*/
        removeTask(taskJobDTO.getId());
    }

    /**
     * 获取cron表达式
     * @param time
     * @return
     */
    private String getCron(Date time) {
        try {
            Calendar now = Calendar.getInstance();
//            Date date = new Date(time.getTime());
            now.setTime(time);
            LOGGER.info(String.format(time + "转化为：%d年%d月%d日%d时%d分%d秒", now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH), now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), now.get(Calendar.SECOND)));
            return String.format("%d %d %d %d %d ?", now.get(Calendar.SECOND), now.get(Calendar.MINUTE), now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.DAY_OF_MONTH), now.get(Calendar.MONTH) + 1);
        } catch (Exception e) {
            throw new ResultException(ResultUtil.TIME_INTRAFROM_FAIL, ResultUtil.getMessage(ResultUtil.TIME_INTRAFROM_FAIL));
        }
    }
}
