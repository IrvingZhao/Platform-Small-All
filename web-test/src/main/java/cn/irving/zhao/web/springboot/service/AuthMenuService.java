package cn.irving.zhao.web.springboot.service;

import cn.irving.zhao.web.springboot.dao.AuthMenuDao;
import cn.irving.zhao.web.springboot.entity.AuthMenu;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by irvin on 2018/1/3.
 */
@Service
public class AuthMenuService {

    @Resource
    AuthMenuDao authMenuDao;

    public List<AuthMenu> getMenus() {
        return authMenuDao.selectAll();
    }

    public void saveAuthMenu(AuthMenu authMenu) {
        authMenuDao.save(authMenu);
    }

    public void saveAuthMenuEx(AuthMenu authMenu) {
        authMenuDao.save(authMenu);

        throw new NullPointerException("测试Exception");

    }

}
