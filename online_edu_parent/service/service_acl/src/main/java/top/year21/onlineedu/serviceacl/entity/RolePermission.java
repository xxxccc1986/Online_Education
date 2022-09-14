package top.year21.onlineedu.serviceacl.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 角色权限
 * </p>
 *
 * @author year21
 * @since 2022-09-04
 */
@TableName("acl_role_permission")
@ApiModel(value = "RolePermission对象", description = "角色权限")
public class RolePermission implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private String id;

    private String roleId;

    private String permissionId;

    @ApiModelProperty("逻辑删除 1（true）已删除， 0（false）未删除")
    @TableLogic
    private Integer isDeleted;

    @ApiModelProperty("创建时间")
    @TableField(value = "gmt_create",fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty("更新时间")
    @TableField(value = "gmt_modified",fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }
    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }
    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /*
    重写了equals和hashcode方法用于判断List集合中是否已存在当前需要添加的数据，避免重复添加
     */
    @Override
    public int hashCode() {
        return Objects.hash(id,roleId,permissionId,isDeleted,gmtCreate,gmtModified);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj){//判断比较的两个数的地址值是否相同
            return true;
        }
        if (obj instanceof RolePermission){//判断传入的形参是否是当前类或其子类实例对象
            //强转为当前类型
            RolePermission o = (RolePermission)obj;
            return this.roleId.equals(o.roleId) && this.permissionId.equals(o.permissionId);
        } else {
            return  false;
        }
    }

    @Override
    public String toString() {
        return "RolePermission{" +
            "id=" + id +
            ", roleId=" + roleId +
            ", permissionId=" + permissionId +
            ", isDeleted=" + isDeleted +
            ", gmtCreate=" + gmtCreate +
            ", gmtModified=" + gmtModified +
        "}";
    }
}
