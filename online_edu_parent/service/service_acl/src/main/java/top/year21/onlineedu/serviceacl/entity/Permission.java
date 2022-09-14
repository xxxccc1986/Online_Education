package top.year21.onlineedu.serviceacl.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 权限
 * </p>
 *
 * @author year21
 * @since 2022-09-04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("acl_permission")
@ApiModel(value = "Permission对象", description = "权限")
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("编号")
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty("所属上级")
    private String pid;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("类型(1:菜单,2:按钮)")
    private Integer type;

    @ApiModelProperty("权限值")
    private String permissionValue;

    @ApiModelProperty("访问路径")
    private String path;

    @ApiModelProperty("组件路径")
    private String component;

    @ApiModelProperty("图标")
    private String icon;

    @ApiModelProperty("状态(0:禁止,1:正常)")
    private Integer status;

    @ApiModelProperty("逻辑删除 1（true）已删除， 0（false）未删除")
    @TableLogic
    private Integer isDeleted;

    @ApiModelProperty("创建时间")
    @TableField(value = "gmt_create",fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty("更新时间")
    @TableField(value = "gmt_modified",fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    @ApiModelProperty("当前级别")
    @TableField(exist = false)
    private Integer level;

    @ApiModelProperty("子菜单")
    @TableField(exist = false)
    private List<Permission> children;

    @ApiModelProperty("是否选中")
    @TableField(exist = false)
    private boolean isSelect;

}
