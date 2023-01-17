package cn.mgl.purity.mapper;

import cn.mgl.purity.controller.RoleController;
import cn.mgl.purity.model.persistent.Role;
import cn.mgl.purity.model.persistent.RoleDTO;
import cn.mgl.purity.model.persistent.RoleMenuRel;

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
