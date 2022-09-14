package top.year21.onlineedu.statistics.service;

import top.year21.onlineedu.statistics.entity.Daily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author year21
 * @since 2022-09-01
 */
public interface IDailyService extends IService<Daily> {

    void countRegisterNumInOneDay(String day);

    Map<String, Object> showTableData(String type, String begin, String end);
}
