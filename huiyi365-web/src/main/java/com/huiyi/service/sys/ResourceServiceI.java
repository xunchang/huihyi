package com.huiyi.service.sys;

import java.util.List;

import com.huiyi.view.base.SessionInfo;
import com.huiyi.view.base.Tree;
import com.huiyi.view.sys.ResourceView;

public interface ResourceServiceI {

    public List<Tree> tree(SessionInfo sessionInfo);//获取节点树列表
    public List<ResourceView> treeGrid();//获取内容树列表
    
    public void edit(ResourceView resourceView);//资源编辑修改
    public void delete(Long id);//资源删除
    
    public List<String> listAllResource();
    
    public ResourceView get(Long id);//根据id查询资源
    
    public void add(ResourceView resourceView);//添加资源
    
    public List<Tree> listAllTree(boolean flag);//获取菜单资源，true获取全部，false之获取菜单
}
