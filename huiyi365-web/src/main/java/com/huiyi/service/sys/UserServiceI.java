package com.huiyi.service.sys;

import java.util.List;

import com.huiyi.view.base.SessionInfo;
import com.huiyi.view.sys.UserView;

public interface UserServiceI {
    
    public UserView login(UserView user);//登录
    
    public boolean editUserPwd(SessionInfo sessionInfo, String oldPwd, String pwd);//修改密码
    public List<String> listResource(Long id);//根据id查找资源url

    
    /*public List<User> dataGrid(User user, PageFilter ph);

    public Long count(User user, PageFilter ph);

    public void add(User user);

    public void delete(Long id);

    public void edit(User user);

    public User get(Long id);




    public User getByLoginName(User user);

    public List<User> getUserListByUserType();

    public String[] getUserListNameByUserType();*/
}
