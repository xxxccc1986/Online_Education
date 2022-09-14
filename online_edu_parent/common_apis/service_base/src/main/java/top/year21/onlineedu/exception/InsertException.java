package top.year21.onlineedu.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hcxs1986
 * @version 1.0
 * @description: 表示插入操作异常
 * @date 2022/8/18 23:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InsertException extends RuntimeException{
    private Integer code;//状态码
    private String message; //异常信息
}
