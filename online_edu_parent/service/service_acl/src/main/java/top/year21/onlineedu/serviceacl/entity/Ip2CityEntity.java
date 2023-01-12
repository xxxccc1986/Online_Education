package top.year21.onlineedu.serviceacl.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author hcxs1986
 * @version 1.0
 * @description: 用来保存查询后的IP属地信息
 * @date 2023/1/2 15:14
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class Ip2CityEntity {
    private static final long serialVersionUID = 1L;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 国家名称
     */
    private String country;

    /**
     * 省份名称
     */
    private String province;

    /**
     * 城市名称
     */
    private String city;

    /**
     * 经度
     */
    private Double longitude;

    /**
     * 维度
     */
    private Double latitude;

    /**
     * 查询耗时
     */
    private String cost;
}
