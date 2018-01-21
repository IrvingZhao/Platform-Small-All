package cn.irving.zhao.web.controller;

import cn.irving.zhao.web.entity.AuthMenu;
import cn.irving.zhao.web.service.AuthMenuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * Created by irvin on 2018/1/3.
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @Resource
    AuthMenuService authMenuService;

    @RequestMapping("/a")
    @ResponseBody
    public String methodA() {
        return "methodA";
    }


    @ResponseBody
    @RequestMapping("/list")
    public List<AuthMenu> getMenus() {
        return authMenuService.getMenus();
    }

    @ResponseBody
    @RequestMapping("/save")
    public String saveMenus() {
        AuthMenu authMenu = new AuthMenu();

        authMenu.setId(UUID.randomUUID().toString().replace("-", ""));
        authMenu.setIsDelete("N");
        authMenu.setMenuAddress("MenuAddress");
        authMenu.setParentMenu("parentMenu");
        authMenu.setStatus("status");

        authMenuService.saveAuthMenu(authMenu);
        return "saved";
    }

    @ResponseBody
    @RequestMapping("/save")
    public String saveMenusEx() {
        AuthMenu authMenu = new AuthMenu();

        authMenu.setId(UUID.randomUUID().toString().replace("-", ""));
        authMenu.setIsDelete("N");
        authMenu.setMenuAddress("MenuAddress");
        authMenu.setParentMenu("parentMenu");
        authMenu.setStatus("status");
        authMenuService.saveAuthMenuEx(authMenu);
        return "saved";
    }

}
