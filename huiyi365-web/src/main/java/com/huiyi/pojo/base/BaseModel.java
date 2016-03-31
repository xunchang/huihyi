package com.huiyi.pojo.base;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * 
* @ClassName: BaseModel 
* @Description: 统一定义id,创建时间,更新时间基类.基类统一定义id的属性名称、数据类型、列名映射及生成策略.
* @author WXCH
* @date 2016年3月30日 下午4:31:19 
*
 */
@MappedSuperclass
public abstract class BaseModel {

    protected Long id;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
