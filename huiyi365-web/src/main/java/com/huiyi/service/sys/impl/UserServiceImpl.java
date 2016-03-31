package com.huiyi.service.sys.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huiyi.dao.BaseDaoI;
import com.huiyi.pojo.sys.Resource;
import com.huiyi.pojo.sys.Role;
import com.huiyi.pojo.sys.User;
import com.huiyi.service.sys.UserServiceI;
import com.huiyi.utils.MD5Util;
import com.huiyi.view.base.SessionInfo;
import com.huiyi.view.sys.UserView;

@Service("userService")
public class UserServiceImpl implements UserServiceI {

    @Autowired
    private BaseDaoI<User> userDao;
    
    @Override
    @Transactional
    public UserView login(UserView user) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("loginname", user.getLoginname());
        params.put("password", MD5Util.md5(user.getPassword()));
        //User u = userDao.get("from User u where u.loginname = :loginname and u.password = :password", params);
        User u = userDao.get("from User u where u.loginname = :loginname and u.password = :password", params);
        if (u != null) {
            UserView uv = new UserView();
            BeanUtils.copyProperties(u, uv);
            return uv;
        }
        return null;
    }

    @Override
    public boolean editUserPwd(SessionInfo sessionInfo, String oldPwd, String pwd) {
        User u = userDao.get(User.class, sessionInfo.getId());
        if (u.getPassword().equalsIgnoreCase(MD5Util.md5(oldPwd))) {// 说明原密码输入正确
            u.setPassword(MD5Util.md5(pwd));
            userDao.update(u);
            return true;
        }
        return false;
    }
    
    @Override
    public List<String> listResource(Long id) {
        List<String> resourceList = new ArrayList<String>();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        User t = userDao.get("from User u join fetch u.roles role join fetch role.resources resource where u.id = :id",
                params);
        if (t != null) {
            Set<Role> roles = t.getRoles();
            if ((roles != null) && !roles.isEmpty()) {
                for (Role role : roles) {
                    Set<Resource> resources = role.getResources();
                    if ((resources != null) && !resources.isEmpty()) {
                        for (Resource resource : resources) {
                            if ((resource != null) && (resource.getUrl() != null)) {
                                resourceList.add(resource.getUrl());
                            }
                        }
                    }
                }
            }
        }
        return resourceList;
    }


    /*@Autowired
    private BaseDaoI<Tuser> userDao;

    @Autowired
    private BaseDaoI<Trole> roleDao;

    @Autowired
    private BaseDaoI<Torganization> organizationDao;

    @Override
    public void add(User u) {
        Tuser t = new Tuser();
        BeanUtils.copyProperties(u, t);
        t.setOrganization(organizationDao.get(Torganization.class, u.getOrganizationId()));

        List<Trole> roles = new ArrayList<Trole>();
        if (u.getRoleIds() != null) {
            for (String roleId : u.getRoleIds().split(",")) {
                roles.add(roleDao.get(Trole.class, Long.valueOf(roleId)));
            }
        }
        t.setRoles(new HashSet<Trole>(roles));

        t.setPassword(MD5Util.md5(u.getPassword()));
        t.setState(GlobalConstant.ENABLE);
        t.setIsdefault(GlobalConstant.DEFAULT);
        t.setCreatedatetime(new Date());
        userDao.save(t);
    }

    @Override
    public void delete(Long id) {
        Tuser t = userDao.get(Tuser.class, id);
        del(t);
    }

    private void del(Tuser t) {
        userDao.delete(t);
    }

    @Override
    public void edit(User user) {
        Tuser t = userDao.get(Tuser.class, user.getId());
        t.setAge(user.getAge());
        t.setLoginname(user.getLoginname());
        t.setName(user.getName());
        t.setOrganization(organizationDao.get(Torganization.class, user.getOrganizationId()));
        List<Trole> roles = new ArrayList<Trole>();
        if (user.getRoleIds() != null) {
            for (String roleId : user.getRoleIds().split(",")) {
                roles.add(roleDao.get(Trole.class, Long.valueOf(roleId)));
            }
        }
        t.setRoles(new HashSet<Trole>(roles));
        t.setSex(user.getSex());
        t.setUsertype(user.getUsertype());
        t.setPhone(user.getPhone());
        t.setState(user.getState());
        if ((user.getPassword() != null) && !"".equals(user.getPassword())) {
            t.setPassword(MD5Util.md5(user.getPassword()));
        }
        userDao.update(t);
    }

    @Override
    public User get(Long id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        Tuser t = userDao.get("from Tuser t  left join fetch t.roles role where t.id = :id", params);
        User u = new User();
        BeanUtils.copyProperties(t, u);

        if (t.getOrganization() != null) {
            u.setOrganizationId(t.getOrganization().getId());
            u.setOrganizationName(t.getOrganization().getName());
        }

        if ((t.getRoles() != null) && !t.getRoles().isEmpty()) {
            String roleIds = "";
            String roleNames = "";
            boolean b = false;
            for (Trole role : t.getRoles()) {
                if (b) {
                    roleIds += ",";
                    roleNames += ",";
                } else {
                    b = true;
                }
                roleIds += role.getId();
                roleNames += role.getName();
            }
            u.setRoleIds(roleIds);
            u.setRoleNames(roleNames);
        }
        return u;
    }

    @Override
    public List<User> dataGrid(User user, PageFilter ph) {
        List<User> ul = new ArrayList<User>();
        Map<String, Object> params = new HashMap<String, Object>();
        String hql = " from Tuser t ";
        List<Tuser> l = userDao.find(hql + whereHql(user, params) + orderHql(ph), params, ph.getPage(),
                ph.getRows());
        for (Tuser t : l) {
            User u = new User();
            BeanUtils.copyProperties(t, u);
            Set<Trole> roles = t.getRoles();
            if ((roles != null) && !roles.isEmpty()) {
                String roleIds = "";
                String roleNames = "";
                boolean b = false;
                for (Trole tr : roles) {
                    if (b) {
                        roleIds += ",";
                        roleNames += ",";
                    } else {
                        b = true;
                    }
                    roleIds += tr.getId();
                    roleNames += tr.getName();
                }
                u.setRoleIds(roleIds);
                u.setRoleNames(roleNames);
            }
            if (t.getOrganization() != null) {
                u.setOrganizationId(t.getOrganization().getId());
                u.setOrganizationName(t.getOrganization().getName());
            }
            ul.add(u);
        }
        return ul;
    }

    @Override
    public Long count(User user, PageFilter ph) {
        Map<String, Object> params = new HashMap<String, Object>();
        String hql = " from Tuser t ";
        return userDao.count("select count(*) " + hql + whereHql(user, params), params);
    }

    private String whereHql(User user, Map<String, Object> params) {
        String hql = "";
        if (user != null) {
            hql += " where 1=1 ";
            if (user.getName() != null) {
                hql += " and t.name like :name";
                params.put("name", "%%" + user.getName() + "%%");
            }
            if (user.getCreatedatetimeStart() != null) {
                hql += " and t.createdatetime >= :createdatetimeStart";
                params.put("createdatetimeStart", user.getCreatedatetimeStart());
            }
            if (user.getCreatedatetimeEnd() != null) {
                hql += " and t.createdatetime <= :createdatetimeEnd";
                params.put("createdatetimeEnd", user.getCreatedatetimeEnd());
            }
            if (user.getOrganizationId() != null) {
                hql += " and t.organization.id =" + user.getOrganizationId();
            }
        }
        return hql;
    }

    private String orderHql(PageFilter ph) {
        String orderString = "";
        if ((ph.getSort() != null) && (ph.getOrder() != null)) {
            orderString = " order by t." + ph.getSort() + " " + ph.getOrder();
        }
        return orderString;
    }


    @Override
    public User getByLoginName(User user) {
        Tuser t = userDao.get("from Tuser t  where t.loginname = '" + user.getLoginname() + "'");
        User u = new User();
        if (t != null) {
            BeanUtils.copyProperties(t, u);
        } else {
            return null;
        }
        return u;
    }

    @Override
    public List<User> getUserListByUserType() {
        List<Tuser> list = userDao.find("from Tuser t where t.usertype=1 and t.state=0");
        List<User> users = new ArrayList<User>();
        for (int i = 0; i < list.size(); i++) {
            User u = new User();
            BeanUtils.copyProperties(list.get(i), u);
            users.add(u);
        }
        return users;
    }

    @Override
    public String[] getUserListNameByUserType() {
        List<Tuser> list = userDao.find("from Tuser t where t.usertype=1 and t.state=0");
        String[] users = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            users[i] = list.get(i).getName();
        }
        return users;
    }*/
}
