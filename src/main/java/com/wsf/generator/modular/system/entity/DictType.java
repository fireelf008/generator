package com.wsf.generator.modular.system.entity;

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

/**
 * 字典类型
 * @author wsf
 */
@Data
@Entity(name = "sys_dict_type")
@EntityListeners(value = AuditingEntityListener.class)
public class DictType implements Serializable {

    @ApiModelProperty(value = "id")
    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "com.wsf.generator.utils.IdUtils")
    @Column(name = "id")
    private Long id;

    @ApiModelProperty(value = "字典类型code")
    @Column(name = "type_code")
    private String typeCode;

    @ApiModelProperty(value = "字典类型名称")
    @Column(name = "type_name")
    private String typeName;

    @ApiModelProperty(value = "字典类型说明")
    @Column(name = "description")
    private String description;

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

    @ApiModelProperty(value = "字典集合")
    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    @org.hibernate.annotations.ForeignKey(name = "none")
    private List<Dict> dictList;
}
