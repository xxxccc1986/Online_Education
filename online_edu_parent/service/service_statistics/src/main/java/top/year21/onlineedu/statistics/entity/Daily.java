package top.year21.onlineedu.statistics.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 网站统计日数据
 * </p>
 *
 * @author year21
 * @since 2022-09-01
 */
@TableName("statistics_daily")
@ApiModel(value = "Daily对象", description = "网站统计日数据")
public class Daily implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty("统计日期")
    private String dateCalculated;

    @ApiModelProperty("注册人数")
    private Integer registerNum;

    @ApiModelProperty("登录人数")
    private Integer loginNum;

    @ApiModelProperty("每日播放视频数")
    private Integer videoViewNum;

    @ApiModelProperty("每日新增课程数")
    private Integer courseNum;

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
    public String getDateCalculated() {
        return dateCalculated;
    }

    public void setDateCalculated(String dateCalculated) {
        this.dateCalculated = dateCalculated;
    }
    public Integer getRegisterNum() {
        return registerNum;
    }

    public void setRegisterNum(Integer registerNum) {
        this.registerNum = registerNum;
    }
    public Integer getLoginNum() {
        return loginNum;
    }

    public void setLoginNum(Integer loginNum) {
        this.loginNum = loginNum;
    }
    public Integer getVideoViewNum() {
        return videoViewNum;
    }

    public void setVideoViewNum(Integer videoViewNum) {
        this.videoViewNum = videoViewNum;
    }
    public Integer getCourseNum() {
        return courseNum;
    }

    public void setCourseNum(Integer courseNum) {
        this.courseNum = courseNum;
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

    @Override
    public String toString() {
        return "Daily{" +
            "id=" + id +
            ", dateCalculated=" + dateCalculated +
            ", registerNum=" + registerNum +
            ", loginNum=" + loginNum +
            ", videoViewNum=" + videoViewNum +
            ", courseNum=" + courseNum +
            ", gmtCreate=" + gmtCreate +
            ", gmtModified=" + gmtModified +
        "}";
    }
}
