package com.huiyi.controller.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huiyi.controller.base.BaseController;
import com.huiyi.framework.constant.GlobalConstant;
import com.huiyi.service.sys.ResourceServiceI;
import com.huiyi.service.sys.UserServiceI;
import com.huiyi.view.base.Json;
import com.huiyi.view.base.SessionInfo;
import com.huiyi.view.sys.UserView;

@Controller
@RequestMapping("/admin")
public class IndexController extends BaseController {

    @Autowired
    private UserServiceI userService;
    
    @Autowired
    private ResourceServiceI resourceService;

    @RequestMapping("/index")
    public String index(HttpServletRequest request) {
        SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
        if ((sessionInfo != null) && (sessionInfo.getId() != null)) {
            return "/index";
        }
        return "/login";
    }

   @ResponseBody
    @RequestMapping("/login")
    public Json login(UserView user, HttpSession session) {
        Json json = new Json();
        UserView sysuser = userService.login(user);
        if (sysuser != null) {
            json.setSuccess(true);
            json.setMsg("登陆成功！");

            SessionInfo sessionInfo = new SessionInfo();
            sessionInfo.setId(sysuser.getId());
            sessionInfo.setLoginname(sysuser.getLoginname());
            sessionInfo.setName(sysuser.getName());
            //user.setIp(IpUtil.getIpAddr(getRequest()));
            sessionInfo.setResourceList(userService.listResource(sysuser.getId()));
            sessionInfo.setResourceAllList(resourceService.listAllResource());
            session.setAttribute(GlobalConstant.SESSION_INFO, sessionInfo);
        } else {
            json.setMsg("用户名或密码错误！");
        }
        return json;
    }
    @ResponseBody
    @RequestMapping("/logout")
    public Json logout(HttpSession session) {
        Json json = new Json();
        if (session != null) {
            session.invalidate();
        }
        json.setSuccess(true);
        json.setMsg("注销成功！");
        return json;
    }

}
