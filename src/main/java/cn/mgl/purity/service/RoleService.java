package cn.mgl.purity.service;

import cn.mgl.purity.converter.p2s.RoleP2SConverter;
import cn.mgl.purity.dao.RoleDao;
import cn.mgl.purity.model.persistent.Role;
import cn.mgl.purity.model.persistent.RoleDTO;
import cn.mgl.purity.model.persistent.RoleMenuRel;
import cn.mgl.purity.model.persistent.User;
import com.github.pagehelper.PageHelper;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
//@Transactional(propagation = Propagation.SUPPORTS)
public class RoleService {

    private final RoleDao roleDao;
    private final RoleP2SConverter roleP2SConverter;

    public RoleService(RoleDao roleDao, RoleP2SConverter roleP2SConverter) {
        this.roleDao = roleDao;
        this.roleP2SConverter = roleP2SConverter;
    }

    /**
     * 清除菜单
     *
     * @param menuId
     */
    public void cleanMenus(Long menuId) {
        roleDao.clearMenus(menuId);
    }

    public List<Role> getAllRoles() {
        return roleDao.getAll();
    }

    @Transactional(rollbackFor = Exception.class)
    public void createRole(RoleDTO roleDTO) {
        Role convert = roleP2SConverter.reverse().convert(roleDTO);
        roleDao.insert(convert);
        if (roleDTO.getPermissionRoles().size() > 0) {
            roleDao.batchInsertRel(convert.getId(), roleDTO.getPermissionRoles());
        }
    }

    public Role getRoleByName(String name) {
        return roleDao.getByName(name);
    }

    public List<RoleDTO> getList(Role role, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<Role> roles = roleDao.selectByPage(role);
        List<RoleDTO> roleDTOList = roles.stream().map(roleP2SConverter::convert).collect(Collectors.toList());
        roleDTOList.forEach(roleDTO -> {
            roleDTO.setPermissionRoles(roleDao.getRoleMenus(roleDTO.getId()).stream().map(RoleMenuRel::getMenuId).collect(Collectors.toList()));
        });
        return roleDTOList;
    }

    /**
     * 根绝角色id获取角色信息
     *
     * @param id
     * @return
     */
    public RoleDTO getRoleById(Long id) {
        Role roleById = roleDao.getById(id);
        if (roleById == null) {
            throw new RuntimeException("id为【" + id + "】的角色不存在，请检查传递参数");
        }
        RoleDTO convert = roleP2SConverter.convert(roleById);
        convert.setPermissionRoles(roleDao.getRoleMenus(convert.getId()).stream().map(RoleMenuRel::getMenuId).collect(Collectors.toList()));
        return convert;
    }

//    public void UpdatePermission(Long id){
//        roleDao.bindRolesByUserId(id);
//    }

    /**
     * 更新角色
     *
     * @param roleDTO
     * @return
     */
    public Role updateRole(RoleDTO roleDTO) {
        Role convert = roleP2SConverter.reverse().convert(roleDTO);
        roleDao.update(convert);
        roleDao.clearMenus(roleDTO.getId());
        if (roleDTO.getPermissionRoles().size() > 0) {
            roleDao.batchInsertRel(roleDTO.getId(), roleDTO.getPermissionRoles());
        }
        return roleDao.getById(roleDTO.getId());
    }

    public void deleteRole(Long id) {
        val roleById = roleDao.getById(id);
        if (roleById == null) {
            throw new RuntimeException("删除失败，id为【" + id + "】的角色不存在");
        }
        roleDao.delete(id);
    }

    public void getMenuByUser(User userByName) {
    }
}
