package com.example.demo.service;

import com.example.demo.dao.RoleDao;
import com.example.demo.model.persistent.Role;
import com.example.demo.model.persistent.User;
import com.github.pagehelper.PageHelper;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class RoleService {

    private final RoleDao roleDao;

    public RoleService(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public List<Role> getAllRoles() {
        return roleDao.getAll();
    }

    public void createRole(Role role) {
        roleDao.createRole(role);
    }

    public Role getRoleByName(String name) {
        return roleDao.getRoleByName(name);
    }

    public List<Role> getList(Role role, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        return roleDao.getList(role);
    }

    public Role getRoleById(Long id) {
        val roleById = roleDao.getRoleById(id);
        if (roleById == null) {
            throw new RuntimeException("id为【" + id + "】的角色不存在，请检查传递参数");
        }
        return roleById;
    }

    public Role updateRole(Role role) {
        roleDao.updateRole(role);
        return roleDao.getRoleById(role.getId());
    }

    public void deleteRole(Long id) {
        val roleById = roleDao.getRoleById(id);
        if (roleById == null) {
            throw new RuntimeException("删除失败，id为【" + id + "】的角色不存在");
        }
        roleDao.deleteRole(id);
    }

    public void getMenuByUser(User userByName) {
    }
}
