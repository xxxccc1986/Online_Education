package easyexcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import easyexcel.excelentity.DataEntity;

import java.util.Map;

/**
 * @author hcxs1986
 * @version 1.0
 * @description: 创建监听器进行对excel文件内容的读取
 * @date 2022/8/21 22:02
 */
public class ExcelReadListener extends AnalysisEventListener<DataEntity> {
    //逐行读取excel的内容
    @Override
    public void invoke(DataEntity dataEntity, AnalysisContext analysisContext) {
        System.out.println("------" + dataEntity + "------");
    }

    //读取每列表头信息
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头内容:" + headMap);
    }

    //读取完成之后执行
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
