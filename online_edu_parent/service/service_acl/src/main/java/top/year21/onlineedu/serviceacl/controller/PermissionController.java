package top.year21.onlineedu.serviceacl.controller;


import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.year21.onlineedu.JsonResult;
import top.year21.onlineedu.serviceacl.entity.Permission;
import top.year21.onlineedu.serviceacl.service.IPermissionService;

import java.util.List;

/**
 * <p>
 * 权限 前端控制器
 * </p>
 *
 * @author year21
 * @since 2022-09-04
 */
@RestController
@RequestMapping("/admin/acl/permission")
public class PermissionController {

    @Autowired
    private IPermissionService permissionService;

    //查询所有菜单
    @GetMapping("/queryAll")
    public JsonResult<List<Permission>> queryAllPermission(){
        List<Permission> list = permissionService.queryAllPermission();
        return new JsonResult<>(true,"查询成功",list);
    }

    //删除菜单
    @PostMapping("/delAll/{id}")
    public JsonResult<Void> delAllPermission(@PathVariable("id") String id){
        permissionService.delPermissionById(id);
        return new JsonResult<>(true);
    }

    //给角色分配权限
    @PostMapping("/givePermission")
    public JsonResult<Void> givePermission(String roleId,String[] permissionId){
        permissionService.givePermission(roleId,permissionId);
        return new JsonResult<>(true);
    }

    @ApiOperation(value = "根据角色获取菜单")
    @GetMapping("/toAssign/{roleId}")
    public JsonResult<List<Permission>> toAssign(@PathVariable String roleId) {
        List<Permission> list = permissionService.selectAllMenu(roleId);
        return new JsonResult<>(true,"查询成功",list);
    }

    @ApiOperation(value = "新增菜单")
    @PostMapping("/save")
    public JsonResult<Void> save(@RequestBody Permission permission) {
        permissionService.save(permission);
        return new JsonResult<>(true);
    }

    @ApiOperation(value = "修改菜单")
    @PostMapping("/update")
    public JsonResult<Void> updateById(@RequestBody Permission permission) {
        permissionService.updateById(permission);
        return new JsonResult<>(true);
    }


}
