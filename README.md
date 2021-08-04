# springschedultask
springboot+maven项目
spring自带的定时任务，没有设计到数据库。



一种定时任务的简单书写，主要用到了ScheduledTaskRegistrar 、CronTask、ScheduledTask这三个类，

CronTask  主要负责定时的频率以及任务的执行。

ScheduledTaskRegistrar 将 CronTask 注册到计划中。

ScheduledTask 主要是对计划进行操作，如取消计划，则任务不会执行。


这是主要是用的是spring3.0   cron只能是6位  不支持年的表达式
