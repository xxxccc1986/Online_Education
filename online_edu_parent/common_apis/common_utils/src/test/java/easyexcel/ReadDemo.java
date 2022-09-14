package easyexcel;

import com.alibaba.excel.EasyExcel;
import easyexcel.excelentity.DataEntity;

/**
 * @author hcxs1986
 * @version 1.0
 * @description: 实现excel读操作
 * @date 2022/8/21 22:06
 */
public class ReadDemo {
    public static void main(String[] args) {
        //1.设置读取文件夹地址和excel文件名称
        String filename = "D:\\write.xlsx";

        //实现excel读操作
        EasyExcel.read(filename, DataEntity.class,new ExcelReadListener()).sheet().doRead();
    }
}
