package cn.mgl.purity.dao;

import cn.mgl.purity.controller.RoleController;
import cn.mgl.purity.model.persistent.RoleDTO;
import cn.mgl.purity.model.persistent.Role;
import cn.mgl.purity.model.persistent.RoleMenuRel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单模块数据持久层
 */
public interface RoleDao {
    int insert(Role data);

    int delete(Long id);

    int update(Role data);

    List<Role> getAll();

    Role getByName(String name);

    Role getById(Long id);

    List<Role> selectByPage(Role role);

    List<RoleMenuRel> getRoleMenus(Long roleId);

    int clearMenus(Long roleId);

    int saveRoleMenus(RoleController.TempRoleMenu roleMenu);

    List<RoleDTO> getUserRolesByUserId(Long menuId, List<Long> menuIdList);

    void bindRolesByUserId();

    void cleanRolesByUserId();

    /**
     * 批量插入角色菜单关系表
     *
     * @param menuId     角色id
     * @param menuIdList 菜单id列表
     * @return
     */
    int batchInsertRel(@Param("roleId") Long menuId, @Param("menuIdList") List<Long> menuIdList);
}
