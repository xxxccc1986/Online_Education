package easyexcel;

import com.alibaba.excel.EasyExcel;
import easyexcel.excelentity.DataEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hcxs1986
 * @version 1.0
 * @description: 实现excel写操作
 * @date 2022/8/21 21:40
 */
public class WriterDemo {
    public static void main(String[] args) {
        //1.设置写入文件夹地址和excel文件名称
        String filename = "D:\\write.xlsx";

        /* 2.调用easyexcel中的方法实现写操作
        write()方法参数说明：第一个参数为文件路径名称，第二个参数为实体类class
         */
        EasyExcel.write(filename, DataEntity.class).sheet("学生列表").doWrite(getList());
    }

    private static List<DataEntity> getList(){
        List<DataEntity> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(new DataEntity(i,"test" + i));
        }
        return list;
    }
}
