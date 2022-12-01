package com.example.demo.mapper;

import com.example.demo.controller.RoleController;
import com.example.demo.model.persistent.Role;
import com.example.demo.model.persistent.RoleDTO;
import com.example.demo.model.persistent.RoleMenuRel;

import java.util.List;

/**
 * @author MongieLee
 * @version 1.0
 * @date 2022/12/1 10:03
 */
public interface RoleMapper {
    int insert(Role data);

    int delete(Long id);

    int update(Role data);

    List<Role> getAll();

    Role getByName(String name);

    Role getById(Long id);

    List<Role> selectByPage(Integer Page, Integer PageSize);

    List<RoleMenuRel> getRoleMenus();

    int clearMenus(Long roleId);

    int saveRoleMenus(RoleController.TempRoleMenu roleMenu);

    List<RoleDTO> getUserRolesByUserId();

    void bindRolesByUserId();

    void cleanRolesByUserId();
}
