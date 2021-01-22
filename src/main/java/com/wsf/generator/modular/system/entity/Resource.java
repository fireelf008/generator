package com.wsf.generator.modular.system.entity;

import com.wsf.generator.utils.RedissonUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 权限
 * @author wsf
 */
@Data
@Entity(name = "sys_resource")
@EntityListeners(value = AuditingEntityListener.class)
public class Resource implements Serializable {

    @ApiModelProperty(value = "id")
    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "com.wsf.generator.utils.IdUtils")
    @Column(name = "id")
    private Long id;

    @ApiModelProperty(value = "权限code")
    @Column(name = "res_code")
    private String resCode;

    @ApiModelProperty(value = "权限名称")
    @Column(name = "res_name")
    private String resName;

    @ApiModelProperty(value = "权限url")
    @Column(name = "url")
    private String url;

    @ApiModelProperty(value = "权限排序")
    @Column(name = "sort")
    private Integer sort = 0;

    @ApiModelProperty(value = "权限图标")
    @Column(name = "icon")
    private String icon;

    @ApiModelProperty(value = "权限类型，0菜单，1按钮")
    @Column(name = "type")
    private Integer type;

    @ApiModelProperty(value = "父权限id")
    @Column(name = "pid")
    private Long pid;

    @ApiModelProperty(value = "父权限code")
    @Column(name = "pcode")
    private String pcode;

    @ApiModelProperty(value = "父权限名称")
    @Column(name = "pname")
    private String pname;

    @ApiModelProperty(value = "备注")
    @Column(name = "remark")
    private String remark;

    @ApiModelProperty(value = "创建人")
    @Column(name = "create_name")
    private String createName;

    @ApiModelProperty(value = "创建时间")
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @Column(name = "create_time")
    private Date createTime;

    @ApiModelProperty(value = "修改人")
    @Column(name = "update_name")
    private String updateName;

    @ApiModelProperty(value = "修改时间")
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    @Column(name = "update_time")
    private Date updateTime;

    @ApiModelProperty(value = "数据是否可用，0不可用，1可用")
    @Column(name = "enable")
    private Integer enable = 1;

    @ApiModelProperty(value = "版本号")
    @Column(name = "version")
    private Integer version;

    @Transient
    private List<Resource> childResourceList;

    @Transient
    private String typeStr;

    @Transient
    private Boolean checked = false;

    public String getTypeStr() {
        Map<String, Map<String, String>> dictTypeMap = (Map<String, Map<String, String>>) RedissonUtils.getObject("dict");
        return dictTypeMap.get("res_type").get(String.valueOf(this.type));
    }
}
