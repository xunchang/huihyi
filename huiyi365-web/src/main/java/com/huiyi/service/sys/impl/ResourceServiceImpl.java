package com.huiyi.service.sys.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huiyi.dao.BaseDaoI;
import com.huiyi.pojo.sys.Resource;
import com.huiyi.service.sys.ResourceServiceI;
import com.huiyi.view.base.SessionInfo;
import com.huiyi.view.base.Tree;
import com.huiyi.view.sys.ResourceView;

@Service
public class ResourceServiceImpl implements ResourceServiceI {
    
    @Autowired 
    private BaseDaoI<Resource> resourceDao;

    @Override
    public List<Tree> tree(SessionInfo sessionInfo) {
        List<Resource> resourceList = null;
        List<Tree> treeList = new ArrayList<Tree>();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("resourcetype", 0);// 菜单类型的资源
        if (sessionInfo != null) {
            if ("admin".equals(sessionInfo.getLoginname())) {
                resourceList = resourceDao.find("select distinct r from Resource r  where r.resourcetype = :resourcetype  order by r.seq",params);
            } else {
                params.put("userId", Long.valueOf(sessionInfo.getId()));// 自查自己有权限的资源
                resourceList = resourceDao.find("select distinct r from Resource r join fetch r.roles role join role.users user where r.resourcetype = :resourcetype and user.id = :userId order by r.seq",params);
            }
        } else {
            return null;
        }
        if ((resourceList != null) && (resourceList.size() > 0)) {
            for (Resource resource : resourceList) {
                Tree tree = new Tree();
                tree.setId(resource.getId().toString());
                if (resource.getResource() != null) {
                    tree.setPid(resource.getResource().getId().toString());
                } else {
                    tree.setState("closed");
                }
                tree.setText(resource.getName());
                tree.setIconCls(resource.getIcon());
                Map<String, Object> attr = new HashMap<String, Object>();
                attr.put("url", resource.getUrl());
                tree.setAttributes(attr);
                treeList.add(tree);
            }
        }
        return treeList;
    }

    @Override
    public List<ResourceView> treeGrid() {
        List<ResourceView> resourceViewList = new ArrayList<ResourceView>();
        List<Resource> resourceList = resourceDao.find("select distinct r from Resource r left join fetch r.resource  order by r.seq");
        if ((resourceList != null) && (resourceList.size() > 0)) {
            for (Resource r : resourceList) {
                ResourceView rv = new ResourceView();
                BeanUtils.copyProperties(r, rv);
                rv.setCstate(r.getState());
                if (r.getResource() != null) {
                    rv.setPid(r.getResource().getId());
                }
                rv.setIconCls(r.getIcon());
                resourceViewList.add(rv);
            }
        }
        return resourceViewList;
    }
    
    @Override
    public void edit(ResourceView rv) {
        Resource r = resourceDao.get(Resource.class, rv.getId());
        r.setDescription(rv.getDescription());
        r.setIcon(rv.getIcon());
        r.setName(rv.getName());
        if ((rv.getPid() != null) && !"".equals(rv.getPid())) {
            r.setResource(resourceDao.get(Resource.class, rv.getPid()));
        }
        r.setResourcetype(rv.getResourcetype());
        r.setSeq(rv.getSeq());
        r.setState(rv.getCstate());
        r.setUrl(rv.getUrl());
        r.setDescription(rv.getDescription());
        resourceDao.update(r);
    }
    @Override
    public void delete(Long id) {
        Resource r = resourceDao.get(Resource.class, id);
        del(r);
    }

    private void del(Resource t) {
        if ((t.getResources() != null) && (t.getResources().size() > 0)) {
            for (Resource r : t.getResources()) {
                del(r);
            }
        }
        resourceDao.delete(t);
    }

    @Override
    public List<String> listAllResource() {
        List<String> resourceList = new ArrayList<String>();
        List<Resource> l = resourceDao.find("select distinct r from Resource r left join fetch r.resource  order by r.seq");
        for (int i = 0; i < l.size(); i++) {
            resourceList.add(l.get(i).getUrl());
        }
        return resourceList;
    }
    
    @Override
    public ResourceView get(Long id) {
        Resource r = resourceDao.get(Resource.class, id);
        ResourceView rv = new ResourceView();
        BeanUtils.copyProperties(r, rv);
        rv.setCstate(r.getState());
        if (r.getResource() != null) {
            rv.setPid(r.getResource().getId());
            rv.setPname(r.getResource().getName());
        }
        return rv;
    }

    @Override
    public void add(ResourceView resourceView) {
        Resource resource = new Resource();
        resource.setCreatedatetime(new Date());
        resource.setDescription(resourceView.getDescription());
        resource.setIcon(resourceView.getIcon());
        resource.setName(resourceView.getName());
        if ((resourceView.getPid() != null) && !"".equals(resourceView.getPid())) {
            resource.setResource(resourceDao.get(Resource.class, resourceView.getPid()));
        }
        resource.setResourcetype(resourceView.getResourcetype());
        resource.setSeq(resourceView.getSeq());
        resource.setState(resourceView.getCstate());
        resource.setUrl(resourceView.getUrl());
        resourceDao.save(resource);
    }

    @Override
    public List<Tree> listAllTree(boolean flag) {
        List<Resource> resourceList = null;
        List<Tree> treeList = new ArrayList<Tree>();
        if (flag) {
            resourceList = resourceDao.find("select distinct r from Resource r left join fetch r.resource  order by r.seq");
        } else {
            resourceList = resourceDao.find("select distinct r from Resource r left join fetch r.resource where r.resourcetype =0 order by r.seq");
        }
        if ((resourceList != null) && (resourceList.size() > 0)) {
            for (Resource resource : resourceList) {
                Tree tree = new Tree();
                tree.setId(resource.getId().toString());
                if (resource.getResource() != null) {
                    tree.setPid(resource.getResource().getId().toString());
                }
                tree.setText(resource.getName());
                tree.setIconCls(resource.getIcon());
                Map<String, Object> attr = new HashMap<String, Object>();
                attr.put("url", resource.getUrl());
                tree.setAttributes(attr);
                treeList.add(tree);
            }
        }
        return treeList;
    }
}
