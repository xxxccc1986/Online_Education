package top.year21.onlineedu;

import org.springframework.util.DigestUtils;

/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/8/28 20:43
 */
public class PasswordEncryptedUtils {

    public static String getPasswordByMD5(String pwd,String salt){
        for (int i = 0; i < 3 ; i++) {
            //md5加密算法的调用
            pwd =  DigestUtils.md5DigestAsHex((salt + pwd + salt).getBytes()).toUpperCase();
        }
        //返回经过加密的结果
        return pwd;
    }
}
