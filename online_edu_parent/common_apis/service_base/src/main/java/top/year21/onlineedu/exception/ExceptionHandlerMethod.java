package top.year21.onlineedu.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import top.year21.onlineedu.JsonResult;

/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/8/18 22:57
 */
//是Controller增强器，作用是给Controller控制器添加统一的操作或处理。
//经常配合@ExceptionHandler来处理异常
@ControllerAdvice
@Slf4j
public class ExceptionHandlerMethod {

    //全局异常处理方法，当出现异常后就会来执行这个方法，
    @ExceptionHandler(value = {Throwable.class})
    @ResponseBody //@ResponseBody将这个方法信息返回
    public JsonResult<String> globalExceptionHandle(Throwable e){
        return new JsonResult<>(false,"服务出现一点小异常，异常信息是",e.getMessage());
    }

    //特定常处理方法，当出现特定异常后就会来执行这个方法，
    @ExceptionHandler(value = {ArithmeticException.class})
    @ResponseBody //@ResponseBody将这个方法信息返回
    public JsonResult<String> specificExceptionHandle(Throwable e){
        return new JsonResult<>(false,"出现了特定异常，异常信息是",e.getMessage());
    }

    //自定义异常处理方法，当出现自定义异常后就会来执行这个方法，
    @ExceptionHandler(value = {InsertException.class,CommonException.class})
    @ResponseBody //@ResponseBody将这个方法信息返回
    public JsonResult<String> customerExceptionHandle(InsertException e){
        log.error(e.getMessage());
        return new JsonResult<>(false,e.getCode(),e.getMessage());
    }


}
