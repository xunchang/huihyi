package com.huiyi.controller.sys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huiyi.controller.base.BaseController;
import com.huiyi.framework.constant.GlobalConstant;
import com.huiyi.service.sys.ResourceServiceI;
import com.huiyi.view.base.Json;
import com.huiyi.view.base.SessionInfo;
import com.huiyi.view.base.Tree;
import com.huiyi.view.sys.ResourceView;

@Controller
@RequestMapping("/resource")
public class ResourceController extends BaseController {

    @Autowired
    private ResourceServiceI resourceService;

    @RequestMapping("/manager")
    public String manager() {
        return "/admin/resource";
    }

    @RequestMapping("/tree")
    @ResponseBody
    public List<Tree> tree(HttpSession session) {
        SessionInfo sessionInfo = (SessionInfo) session.getAttribute(GlobalConstant.SESSION_INFO);
        return resourceService.tree(sessionInfo);
    }

    @RequestMapping("/treeGrid")
    @ResponseBody
    public List<ResourceView> treeGrid() {
        return resourceService.treeGrid();
    }
    
    @RequestMapping("/edit")
    @ResponseBody
    public Json edit(ResourceView resourceView) throws InterruptedException {
        Json j = new Json();
        try {
            resourceService.edit(resourceView);
            j.setSuccess(true);
            j.setMsg("编辑成功！");
        } catch (Exception e) {
            j.setMsg(e.getMessage());
        }
        return j;
    }
    
    @RequestMapping("/delete")
    @ResponseBody
    public Json delete(Long id) {
        Json j = new Json();
        try {
            resourceService.delete(id);
            j.setMsg("删除成功！");
            j.setSuccess(true);
        } catch (Exception e) {
            j.setMsg(e.getMessage());
        }
        return j;
    }
    
    @RequestMapping("/editPage")
    public String editPage(HttpServletRequest request, Long id) {
        ResourceView r = resourceService.get(id);
        request.setAttribute("resource", r);
        return "/admin/resourceEdit";
    }
    
    @RequestMapping("/addPage")
    public String addPage() {
        return "/admin/resourceAdd";
    }
    
    @RequestMapping("/add")
    @ResponseBody
    public Json add(ResourceView resourceView) {
        Json j = new Json();
        try {
            resourceService.add(resourceView);
            j.setSuccess(true);
            j.setMsg("添加成功！");
        } catch (Exception e) {
            j.setMsg(e.getMessage());
        }
        return j;
    }
    
    @RequestMapping("/allTree")
    @ResponseBody
    public List<Tree> allTree(boolean flag) {// true获取全部资源,false只获取菜单资源
        return resourceService.listAllTree(flag);
    }
    /*
     * 


    @RequestMapping("/get")
    @ResponseBody
    public Resource get(Long id) {
        return resourceService.get(id);
    }

    

    */

}
