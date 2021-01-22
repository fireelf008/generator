package com.wsf.generator.modular.test.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wsf.generator.modular.system.entity.Role;
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
 * 用户
 * @author wsf
 */
@Data
@Entity(name = "sys_test")
@EntityListeners(value = AuditingEntityListener.class)
public class Test implements Serializable {

    @ApiModelProperty(value = "id")
    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "com.wsf.generator.utils.IdUtils")
    @Column(name = "id")
    private Long id;

    @ApiModelProperty(value = "用户名")
    @Column(name = "username")
    private String username;

    @ApiModelProperty(value = "密码")
    @Column(name = "password")
    private String password;

    @ApiModelProperty(value = "密码盐")
    @Column(name = "salt")
    private String salt;

    @ApiModelProperty(value = "真实姓名")
    @Column(name = "real_name")
    private String realName;

    @ApiModelProperty(value = "生日")
    @Temporal(TemporalType.DATE)
    @Column(name = "birthday")
    private Date birthday;

    @ApiModelProperty(value = "性别，0男，1女")
    @Column(name = "sex")
    private Integer sex;

    @ApiModelProperty(value = "注册时间")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "reg_time")
    private Date regTime;

    @ApiModelProperty(value = "注册ip")
    @Column(name = "reg_ip")
    private String regIp;

    @ApiModelProperty(value = "电话")
    @Column(name = "phone")
    private String phone;

    @ApiModelProperty(value = "最后登录时间")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_time")
    private Date lastTime;

    @ApiModelProperty(value = "最后登录ip")
    @Column(name = "last_ip")
    private String lastIp;

    @ApiModelProperty(value = "是否是超级管理员，0不是1是")
    @Column(name = "admin")
    private Integer admin;

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

    @ApiModelProperty(value = "角色集合")
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinTable(name = "sys_user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    @org.hibernate.annotations.ForeignKey(name = "none")
    private List<Role> roleList;

    @Transient
    private String sexStr;

    @Transient
    private Long roleId;

    public String getSexStr() {
        Map<String, Map<String, String>> dictTypeMap = (Map<String, Map<String, String>>) RedissonUtils.getObject("dict");
        return dictTypeMap.get("sex").get(String.valueOf(this.sex));
    }
}
