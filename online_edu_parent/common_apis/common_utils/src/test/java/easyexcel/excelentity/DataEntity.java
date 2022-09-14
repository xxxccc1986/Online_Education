package easyexcel.excelentity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/8/21 21:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataEntity {

    //设置excel表头名称和列数索引
    @ExcelProperty(value = "学生编号",index = 0)
    private Integer num;
    @ExcelProperty(value = "学生名字",index = 1)
    private String name;

}
