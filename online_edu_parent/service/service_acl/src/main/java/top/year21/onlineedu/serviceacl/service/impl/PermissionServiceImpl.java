package top.year21.onlineedu.serviceacl.service.impl;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import top.year21.onlineedu.exception.CommonException;
import top.year21.onlineedu.serviceacl.entity.Permission;
import top.year21.onlineedu.serviceacl.entity.RolePermission;
import top.year21.onlineedu.serviceacl.entity.User;
import top.year21.onlineedu.serviceacl.helper.MemuHelper;
import top.year21.onlineedu.serviceacl.mapper.PermissionMapper;
import top.year21.onlineedu.serviceacl.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.year21.onlineedu.serviceacl.service.IUserService;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author year21
 * @since 2022-09-04
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

    @Autowired
    private RolePermissionServiceImpl rolePermissionService;
    @Autowired
    private IUserService userService;


    //获取所有权限列表
    @Override
    public List<Permission> queryAllPermission() {
        //获取所有数据
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        List<Permission> permissionList = this.baseMapper.selectList(wrapper);

        //封装成需要的树形结构
        List<Permission> resultList = buildNeedTreePermission(permissionList);

        return resultList;
    }

    //封装树形结构的具体方法
    public static List<Permission> buildNeedTreePermission(List<Permission> permissionList) {

        //用于封装权限树形结构的数组
        List<Permission> finalTreePermissionList = new ArrayList<>();

        //遍历获取顶层权限并设置其等级为1
        for (Permission p: permissionList) {
            //顶层权限
            if ("0".equals(p.getPid())){
                //设置等级为1
                p.setLevel(1);
                //获取子节点
                finalTreePermissionList.add(selectChildren(p,permissionList));
            }
        }
        return finalTreePermissionList;
    }

    //获取某个一级菜单下的所有子节点
    public static Permission selectChildren(Permission p, List<Permission> permissionList) {
        //先将当前一级菜单的子节点对象初始化
        p.setChildren(new ArrayList<>());

        //封装子节点并递归查询所有子节点
        for (Permission obj : permissionList ){
            if (p.getId().equals(obj.getPid())){ //说明此obj是当前一级菜单的子节点
                //设置obj对象为二级菜单
                obj.setLevel(p.getLevel()+1);
                //放入一级菜单的子节点列表中
                p.getChildren().add(selectChildren(obj,permissionList));
            }
        }
        return p;
    }

    //删除菜单(递归删除所有节点)
    @Override
    public void delPermissionById(String id) {
        //先查询是否有子节点
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.eq("pid",id);
        List<Permission> delList = this.baseMapper.selectList(wrapper);

        if (delList.size() != 0){//表示有子节点，需要递归删除
            for (Permission node : delList){
                delPermissionById(node.getId());
            }
        }

        //表示没有子节点，直接删除
        int result = this.baseMapper.deleteById(id);
        if (result == 0){
            throw new CommonException(30001,"删除菜单失败");
        }
    }

    //给角色分配权限
    @Override
    public void givePermission(String roleId, String[] permissionIds) {
        //先查出当前rolePermission表中当前角色的所有数据
        QueryWrapper<RolePermission> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id",roleId);
        List<RolePermission> rolePermissionInDB = rolePermissionService.list(wrapper);

        //创建封装RolePermission的集合
        List<RolePermission> rolePermissionList = new ArrayList<>();
        //遍历permissionIds
        for (String pid : permissionIds){
            //先判断当前pid数据是否在数据库存在
            RolePermission needQueryObj = new RolePermission();
            needQueryObj.setRoleId(roleId);
            needQueryObj.setPermissionId(pid);
            boolean result = rolePermissionInDB.contains(needQueryObj);

            //否则表示数据库不存在此条数据可以添加
            if (!result){
                RolePermission rolePermission = new RolePermission();
                rolePermission.setRoleId(roleId);
                rolePermission.setPermissionId(pid);
                rolePermissionList.add(rolePermission);
            }
        }
        //插入数据库
        rolePermissionService.saveBatch(rolePermissionList);
    }

//---------------------------------------------------------------------------

    @Override
    public List<Permission> selectAllMenu(String roleId) {
        List<Permission> allPermissionList = baseMapper.selectList(new QueryWrapper<Permission>().orderByAsc("CAST(id AS SIGNED)"));

        //根据角色id获取角色权限
        List<RolePermission> rolePermissionList = rolePermissionService.list(new QueryWrapper<RolePermission>().eq("role_id",roleId));
        for (int i = 0; i < allPermissionList.size(); i++) {
            Permission permission = allPermissionList.get(i);
            for (int m = 0; m < rolePermissionList.size(); m++) {
                RolePermission rolePermission = rolePermissionList.get(m);
                if(rolePermission.getPermissionId().equals(permission.getId())) {
                    permission.setSelect(true);
                }
            }
        }
        //封装角色权限树形列表
        List<Permission> permissionList = buildNeedTreePermission(allPermissionList);
        return permissionList;
    }

    @Override
    public List<String> selectPermissionValueByUserId(String id) {
        List<String> selectPermissionValueList = null;
        if(this.isSysAdmin(id)) {
            //如果是系统管理员，获取所有权限
            selectPermissionValueList = baseMapper.selectAllPermissionValue();
        } else {
            selectPermissionValueList = baseMapper.selectPermissionValueByUserId(id);
        }
        return selectPermissionValueList;
    }

    @Override
    public List<JSONObject> selectPermissionByUserId(String userId) {
        List<Permission> selectPermissionList = null;
        if(this.isSysAdmin(userId)) {
            //如果是超级管理员，获取所有菜单
            selectPermissionList = baseMapper.selectList(null);
        } else {
            selectPermissionList = baseMapper.selectPermissionByUserId(userId);
        }
        //封装树形权限列表
        List<Permission> permissionList = buildNeedTreePermission(selectPermissionList);
        List<JSONObject> result = MemuHelper.bulid(permissionList);
        return result;
    }

    /**
     * 判断用户是否系统管理员
     * @param userId 用户id
     */
    private boolean isSysAdmin(String userId) {
        User user = userService.getById(userId);

        if(null != user && "admin".equals(user.getUsername())) {
            return true;
        }
        return false;
    }

}
