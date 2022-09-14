package top.year21.onlineedu.servicesms.service;

/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/8/28 16:24
 */
public interface MsgService {
    Boolean sendMessage(String phone, String code);
}
