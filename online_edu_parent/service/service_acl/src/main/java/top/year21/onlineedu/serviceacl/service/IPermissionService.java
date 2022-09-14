package top.year21.onlineedu.serviceacl.service;

import com.alibaba.fastjson.JSONObject;
import top.year21.onlineedu.serviceacl.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 权限 服务类
 * </p>
 *
 * @author year21
 * @since 2022-09-04
 */
public interface IPermissionService extends IService<Permission> {

    //根据角色id获取菜单
    List<Permission> selectAllMenu(String roleId);

    List<Permission> queryAllPermission();

    void delPermissionById(String id);

    void givePermission(String roleId, String[] permissionIds);

    //根据用户id获取用户能够访问的权限列表
    List<String> selectPermissionValueByUserId(String id);

    //根据用户id动态获取用户生成动态路由并封装返回
    List<JSONObject> selectPermissionByUserId(String id);
}
