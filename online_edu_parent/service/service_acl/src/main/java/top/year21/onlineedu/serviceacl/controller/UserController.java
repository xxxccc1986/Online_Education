package top.year21.onlineedu.serviceacl.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import top.year21.onlineedu.JsonResult;
import top.year21.onlineedu.MD5;
import top.year21.onlineedu.serviceacl.entity.User;
import top.year21.onlineedu.serviceacl.service.IRoleService;
import top.year21.onlineedu.serviceacl.service.IUserService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author year21
 * @since 2022-09-04
 */
@RestController
@RequestMapping("/admin/acl/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @ApiOperation(value = "获取管理用户分页列表")
    @GetMapping("{page}/{limit}")
    public JsonResult<Map<String,Object>> index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
                    User userQueryVo) {
        Page<User> pageParam = new Page<>(page, limit);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(userQueryVo.getUsername())) {
            wrapper.like("username",userQueryVo.getUsername());
        }

        IPage<User> pageModel = userService.page(pageParam, wrapper);
        HashMap<String, Object> map = new HashMap<>();
        map.put("items",pageParam.getRecords());
        map.put("total", pageParam.getTotal());
//        return R.ok().data("items", pageModel.getRecords()).data("total", pageModel.getTotal());
        return new JsonResult<>(true,"获取成功",map);

    }

    @ApiOperation(value = "新增管理用户")
    @PostMapping("save")
    public JsonResult<Void> save(@RequestBody User user) {
        user.setPassword(MD5.encrypt(user.getPassword()));
        userService.save(user);
        return new JsonResult<>(true);
    }

    @ApiOperation(value = "修改管理用户")
    @PostMapping("update")
    public JsonResult<Void> updateById(@RequestBody User user) {
        userService.updateById(user);
        return new JsonResult<>(true);
    }

    @ApiOperation(value = "删除管理用户")
    @PostMapping("remove/{id}")
    public JsonResult<Void> remove(@PathVariable String id) {
        userService.removeById(id);
        return new JsonResult<>(true);
    }

    @ApiOperation(value = "根据id列表删除管理用户")
    @PostMapping("batchRemove")
    public JsonResult<Void> batchRemove(@RequestBody List<String> idList) {
        userService.removeByIds(idList);
        return new JsonResult<>(true);
    }

    @ApiOperation(value = "根据用户获取角色数据")
    @GetMapping("/toAssign/{userId}")
    public JsonResult<Map<String, Object>> toAssign(@PathVariable String userId) {
        Map<String, Object> roleMap = roleService.findRoleByUserId(userId);
        return new JsonResult<>(true,"获取成功",roleMap);
    }

    @ApiOperation(value = "根据用户分配角色")
    @PostMapping("/doAssign")
    public JsonResult<Void> doAssign(@RequestParam String userId,@RequestParam String[] roleId) {
        roleService.saveUserRoleRealtionShip(userId,roleId);
        return new JsonResult<>(true);
    }

    @ApiOperation(value = "根据用户id获取用户信息")
    @GetMapping("/get/{id}")
    public JsonResult<User> getUserInfoById(@PathVariable("id") String id){
        User user = userService.getById(id);
        return new JsonResult<>(true,"查询成功",user);
    }
}
