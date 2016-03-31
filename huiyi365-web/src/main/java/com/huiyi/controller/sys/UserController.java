package com.huiyi.controller.sys;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huiyi.controller.base.BaseController;
import com.huiyi.framework.constant.GlobalConstant;
import com.huiyi.pojo.sys.User;
import com.huiyi.service.sys.UserServiceI;
import com.huiyi.view.base.Json;
import com.huiyi.view.base.SessionInfo;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserServiceI userService;

    @RequestMapping("/manager")
    public String manager() {
        return "/admin/user";
    }
    
    @RequestMapping("/editPwdPage")
    public String editPwdPage() {
        return "/admin/userEditPwd";
    }
    
    @RequestMapping("/editUserPwd")
    @ResponseBody
    public Json editUserPwd(HttpServletRequest request, String oldPwd, String pwd) {
        SessionInfo sessionInfo = (SessionInfo) request.getSession()
                .getAttribute(GlobalConstant.SESSION_INFO);
        Json json = new Json();
        try {
            Boolean b = userService.editUserPwd(sessionInfo, oldPwd, pwd);
            if(b){
                json.setSuccess(true);
                json.setMsg("密码修改成功！");
            }else{
                json.setSuccess(false);
                json.setMsg("密码修改失败！");
            }
        } catch (Exception e) {
            json.setMsg(e.getMessage());
        }
        return json;
    }

    /*@RequestMapping("/dataGrid")
    @ResponseBody
    public Grid dataGrid(User user, PageFilter ph) {
        Grid grid = new Grid();
        grid.setRows(userService.dataGrid(user, ph));
        grid.setTotal(userService.count(user, ph));
        return grid;
    }

    

    @RequestMapping("/addPage")
    public String addPage() {
        return "/admin/userAdd";
    }

    @RequestMapping("/add")
    @ResponseBody
    public Json add(User user) {
        Json j = new Json();
        User u = userService.getByLoginName(user);
        if (u != null) {
            j.setMsg("用户名已存在!");
        } else {
            try {
                userService.add(user);
                j.setSuccess(true);
                j.setMsg("添加成功！");
            } catch (Exception e) {
                j.setMsg(e.getMessage());
            }

        }
        return j;
    }

    @RequestMapping("/get")
    @ResponseBody
    public User get(Long id) {
        return userService.get(id);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Json delete(Long id) {
        Json j = new Json();
        try {
            userService.delete(id);
            j.setMsg("删除成功！");
            j.setSuccess(true);
        } catch (Exception e) {
            j.setMsg(e.getMessage());
        }
        return j;
    }

    @RequestMapping("/editPage")
    public String editPage(HttpServletRequest request, Long id) {
        User u = userService.get(id);
        request.setAttribute("user", u);
        return "/admin/userEdit";
    }

    @RequestMapping("/edit")
    @ResponseBody
    public Json edit(User user) {
        Json j = new Json();
        try {
            userService.edit(user);
            j.setSuccess(true);
            j.setMsg("编辑成功！");
        } catch (ServiceException e) {
            j.setMsg(e.getMessage());
        }
        return j;
    }
*/
}
