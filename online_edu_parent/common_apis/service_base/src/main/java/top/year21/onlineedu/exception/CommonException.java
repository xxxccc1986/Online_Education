package top.year21.onlineedu.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/8/28 20:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonException extends RuntimeException{
    private Integer code;//状态码
    private String message; //异常信息
}
