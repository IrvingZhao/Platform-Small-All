package cn.irving.zhao.web.controller;

import cn.irving.zhao.web.entity.AuthMenu;
import cn.irving.zhao.web.service.AuthMenuService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * Created by irvin on 2018/1/21.
 */
@RequestMapping("/test")
@RestController
public class BaseController {

    @Resource
    private AuthMenuService service;

    @RequestMapping("/demo")
    public String demo() {
        return "demo";
    }

    @RequestMapping("/authEx")
    public String authMenuEx() {
        AuthMenu authMenu = new AuthMenu();
        authMenu.setId(UUID.randomUUID().toString().replace("-", ""));

        authMenu.setMenuName("menu name");
        service.saveAuthMenuEx(authMenu);

        return "异常保存";
    }

}
