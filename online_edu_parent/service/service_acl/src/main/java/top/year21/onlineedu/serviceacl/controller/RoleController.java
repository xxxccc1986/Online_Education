package top.year21.onlineedu.serviceacl.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import top.year21.onlineedu.JsonResult;
import top.year21.onlineedu.serviceacl.entity.Role;
import top.year21.onlineedu.serviceacl.service.IRoleService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 */
@RestController
@RequestMapping("/admin/acl/role")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @ApiOperation(value = "获取角色分页列表")
    @GetMapping("{page}/{limit}")
    public JsonResult<Map<String,Object>> index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            Role role) {
        Page<Role> pageParam = new Page<>(page, limit);
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(role.getRoleName())) {
            wrapper.like("role_name",role.getRoleName());
        }
        roleService.page(pageParam,wrapper);
        HashMap<String, Object> map = new HashMap<>();
        map.put("items",pageParam.getRecords());
        map.put("total", pageParam.getTotal());
        return new JsonResult<>(true,"获取成功",map);
    }

    @ApiOperation(value = "获取角色")
    @GetMapping("get/{id}")
    public JsonResult<Role> get(@PathVariable String id) {
        Role role = roleService.getById(id);
        return new JsonResult<>(true,"查询成功",role);
    }

    @ApiOperation(value = "新增角色")
    @PostMapping("save")
    public JsonResult<Void> save(@RequestBody Role role) {
        roleService.save(role);
        return new JsonResult<>(true);
    }

    @ApiOperation(value = "修改角色")
    @PostMapping("update")
    public JsonResult<Role> updateById(@RequestBody Role role) {
        roleService.updateById(role);
        return new JsonResult<>(true);
    }

    @ApiOperation(value = "删除角色")
    @PostMapping("remove/{id}")
    public JsonResult<Role> remove(@PathVariable String id) {
        roleService.removeById(id);
        return new JsonResult<>(true);
    }

    @ApiOperation(value = "根据id列表删除角色")
    @PostMapping("batchRemove")
    public JsonResult<Role> batchRemove(@RequestBody List<String> idList) {
        roleService.removeByIds(idList);
        return new JsonResult<>(true);
    }
}
