package com.my.springtask.utils;

import org.apache.catalina.connector.Response;

import java.util.HashMap;
import java.util.Map;

public class ResultUtil {
    public final static int OK = Response.SC_OK;
    public final static int FAIL = 999;
    public final static int ERROR_SERVER_FAIL_CODE = 500;         //服务器故障
    public final static int TIME_INTRAFROM_FAIL = 501;//时间转化失败
    public final static int CRON_IS_NOT_EMPTY = 502;//cron不能为空
    public final static int ADD_NEW_TASK_FAILED = 503;//新增定时任务失败
    public final static int RESOURCE_IS_NOT_EXIST = 504;//资源不存在



    public final static Map<Integer, String> map = new HashMap<>();

    private int code;

    private String msg;

    static {
        map.put(TIME_INTRAFROM_FAIL, "时间转化失败");
        map.put(CRON_IS_NOT_EMPTY, "cron不能为空");
        map.put(ADD_NEW_TASK_FAILED, "新增定时任务失败");
        map.put(RESOURCE_IS_NOT_EXIST, "资源不存在");
        map.put(ERROR_SERVER_FAIL_CODE, "服务器故障");
    }

    public static String getMessage(int code) {
        String msg = map.get(code);
        return msg == null ? "" : msg;
    }
}
