package top.year21.onlineedu.statistics.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.year21.onlineedu.statistics.service.IDailyService;

import java.time.LocalDate;

/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/9/3 14:50
 */
@Component
public class ScheduleTask {

    @Autowired
    private IDailyService dailyService;

    //0/5 * * * * ?表示每到每天凌晨1点执行一次此方法
    @Scheduled(cron = "0 0 1 * * ? ")
//    @Scheduled(cron = "0/5 * * * * ?")
    public void ScheduleTask(){
        //获取前一天的日期
        LocalDate date = LocalDate.now().plusDays(-1);
        //执行查询并添加操作
        dailyService.countRegisterNumInOneDay(date.toString());
    }
}
