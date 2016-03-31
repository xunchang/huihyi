package com.huiyi.pojo.sys;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotBlank;

import com.huiyi.pojo.base.BaseModel;

@Entity
@Table(name = "sys_organization", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Organization extends BaseModel implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2225413817328841607L;

    private Date createdatetime;//创建日期

    private String name;//组织名称

    private String address;//地址

    private String code;//邮编

    private String icon;//图标

    private Integer seq;

    private Organization organization;

    private Set<Organization> organizations = new HashSet<Organization>(0);

    public Organization() {
        super();
    }

    public Organization(Date createdatetime, String name, String address, String code, String icon,
            Integer seq, Organization organization, Set<Organization> organizations) {
        super();
        this.createdatetime = createdatetime;
        this.name = name;
        this.address = address;
        this.code = code;
        this.icon = icon;
        this.seq = seq;
        this.organization = organization;
        this.organizations = organizations;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pid")
    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdatetime", length = 19)
    public Date getCreatedatetime() {
        return createdatetime;
    }

    public void setCreatedatetime(Date createdatetime) {
        this.createdatetime = createdatetime;
    }

    @NotBlank
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getSeq() {
        return this.seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "organization")
    public Set<Organization> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(Set<Organization> organizations) {
        this.organizations = organizations;
    }

}
