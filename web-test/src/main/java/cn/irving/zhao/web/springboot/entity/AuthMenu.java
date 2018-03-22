package cn.irving.zhao.web.springboot.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by irvin on 2018/1/3.
 */
@Entity
@Table(name = "auth_menu")
public class AuthMenu {

    @Column(name = "id")
    @Id
    private String id;
    @Column(name = "menu_name")
    private String menuName;
    @Column(name = "menu_address")
    private String menuAddress;
    @Column(name = "parent_menu")
    private String parentMenu;
    @Column(name = "menu_status")
    private String status;
    @Column(name = "is_delete")
    private String isDelete;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuAddress() {
        return menuAddress;
    }

    public void setMenuAddress(String menuAddress) {
        this.menuAddress = menuAddress;
    }

    public String getParentMenu() {
        return parentMenu;
    }

    public void setParentMenu(String parentMenu) {
        this.parentMenu = parentMenu;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }
}
