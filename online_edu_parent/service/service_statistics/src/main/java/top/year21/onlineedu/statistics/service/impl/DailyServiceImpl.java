package top.year21.onlineedu.statistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import top.year21.onlineedu.exception.InsertException;
import top.year21.onlineedu.statistics.entity.Daily;
import top.year21.onlineedu.statistics.mapper.DailyMapper;
import top.year21.onlineedu.statistics.service.IDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.year21.onlineedu.statistics.service.USerCenterTransfer;
import top.year21.onlineedu.statistics.utils.RandomNum;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author year21
 * @since 2022-09-01
 */
@Service
public class DailyServiceImpl extends ServiceImpl<DailyMapper, Daily> implements IDailyService {
    @Resource
    private USerCenterTransfer uSerCenterTransfer;

    @Override
    public void countRegisterNumInOneDay(String day) {
        //获取指定日期的注册人数
        Integer num = uSerCenterTransfer.countRegisterNumInOneDay(day);

        //插入之前先判断是否存在该日期的记录
        QueryWrapper<Daily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated",day);
        Daily one = this.baseMapper.selectOne(wrapper);
        if (one != null){
            //将注册人数记录更新
            one.setRegisterNum(num);
            //插入数据库
            this.baseMapper.update(one,wrapper);
            //结束操作
            return;
        }

        //将注册人数的结果插入daily表中
        Daily daily = new Daily();
        daily.setRegisterNum(num);
        daily.setDateCalculated(day);
        daily.setLoginNum(RandomNum.createRandomNum());
        daily.setVideoViewNum(RandomNum.createRandomNum());
        daily.setCourseNum(RandomNum.createRandomNum());

        int result = this.baseMapper.insert(daily);

        if (result == 0){
            throw new InsertException(30001,"插入统计数据异常!");
        }
    }

    //根据类型和时间查询图标数据
    @Override
    public Map<String, Object> showTableData(String type, String begin, String end) {
        QueryWrapper<Daily> wrapper = new QueryWrapper<>();
        //select()方法是根据指定的type字段进行查询
        //between()方法是在指定的时间范围之内
        wrapper.select("date_calculated",type).between("date_calculated",begin,end);
        List<Daily> list = this.baseMapper.selectList(wrapper);

        //拼凑返回前端的数组
        List<String> dateList = new ArrayList<>();
        List<Integer> numList = new ArrayList<>();

        for (Daily d: list) {
            //封装日期
            dateList.add(d.getDateCalculated());
            //封装数字
            switch (type){
                case "register_num":
                    numList.add(d.getRegisterNum());
                    break;
                case "login_num":
                    numList.add(d.getLoginNum());
                    break;
                case "video_view_num":
                    numList.add(d.getVideoViewNum());
                    break;
                case "course_num":
                    numList.add(d.getCourseNum());
                    break;
            }
        }
        //封装到map中并返回
        HashMap<String, Object> map = new HashMap<>();
        map.put("date",dateList);
        map.put("num",numList);

        return map;

    }
}
