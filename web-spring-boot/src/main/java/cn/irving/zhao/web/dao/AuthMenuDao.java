package cn.irving.zhao.web.dao;

import cn.irving.zhao.platform.core.dao.impl.BaseDao;
import cn.irving.zhao.web.entity.AuthMenu;
import cn.irving.zhao.web.mapper.AuthMenuMapper;
import org.springframework.stereotype.Repository;

/**
 * Created by irvin on 2018/1/3.
 */

@Repository
public class AuthMenuDao extends BaseDao<AuthMenu, String, AuthMenuMapper> {
}
