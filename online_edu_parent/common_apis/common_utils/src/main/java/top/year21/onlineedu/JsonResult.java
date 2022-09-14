package top.year21.onlineedu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/8/18 18:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonResult<E> {

    //定义响应码
    private static final Integer SUCCESS = 20000; //成功
    private static final Integer ERROR = 30000; //失败

    private Boolean result;
    private Integer code;
    private String message;
    private E data;

    public JsonResult(Boolean result,String message,E data) {
        if (result){
            this.result = true;
            this.code = SUCCESS;
        }else{
            this.result = false;
            this.code = ERROR;
        }
        this.message = message;
        this.data = data;
    }

    public JsonResult(Boolean result) {
        if (result){
            this.result = true;
            this.code = SUCCESS;
            this.message = "处理成功";
        }else{
            this.result = false;
            this.code = ERROR;
            this.message = "处理失败";
        }
    }

    public JsonResult(Boolean result,Integer code,String message) {
        if (result){
            this.result = true;
            this.code = code;
        }else{
            this.result = false;
            this.code = code;
        }
        this.message = message;
    }

}
