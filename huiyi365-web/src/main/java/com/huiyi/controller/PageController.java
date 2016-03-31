package com.huiyi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
* @ClassName: PageController 
* @Description: 通用页面跳转
* @author WXCH
* @date 2016年1月27日 下午5:30:41 
*
 */
@Controller
@RequestMapping("page")
public class PageController {
	
    @RequestMapping(value = "/{pageName}", method = RequestMethod.GET)
    public String toPage(@PathVariable("pageName") String pageName) {
        return pageName;
    }
    
}
