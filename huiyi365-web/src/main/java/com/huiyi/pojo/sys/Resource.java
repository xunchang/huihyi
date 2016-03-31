package com.huiyi.pojo.sys;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotBlank;

import com.huiyi.pojo.base.BaseModel;

@Entity
@Table(name = "sys_resource")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Resource extends BaseModel implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7708843416844633159L;

    private Date createdatetime; // 创建时间

    private String name; // 名称

    private String url; // 菜单路径

    private String description; // 描述

    private String icon; // 图标

    private Integer seq; // 排序号

    private Integer resourcetype; // 资源类型, 0菜单 1功能

    private Resource resource; // 父级

    private Integer state; // 状态 0启用 1停用

    private Set<Role> roles = new HashSet<Role>(0);

    private Set<Resource> resources = new HashSet<Resource>(0);

    public Resource() {
    }

    public Resource(Long id, Date createdatetime, String name, String url, String description, String icon,
            Integer seq, Integer resourcetype, Resource resource, Integer state) {
        super();
        this.id = id;
        this.createdatetime = createdatetime;
        this.name = name;
        this.url = url;
        this.description = description;
        this.icon = icon;
        this.seq = seq;
        this.resourcetype = resourcetype;
        this.resource = resource;
        this.state = state;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATEDATETIME", length = 19)
    public Date getCreatedatetime() {
        return createdatetime;
    }

    public void setCreatedatetime(Date createdatetime) {
        this.createdatetime = createdatetime;
    }

    @NotBlank
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Integer getResourcetype() {
        return resourcetype;
    }

    public void setResourcetype(Integer resourcetype) {
        this.resourcetype = resourcetype;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pid")
    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "sys_role_resource", joinColumns = { @JoinColumn(name = "resource_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "role_id", nullable = false, updatable = false) })
    @OrderBy("id ASC")
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "resource")
    public Set<Resource> getResources() {
        return resources;
    }

    public void setResources(Set<Resource> resources) {
        this.resources = resources;
    }

}