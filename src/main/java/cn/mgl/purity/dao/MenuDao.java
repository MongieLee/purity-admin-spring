package cn.mgl.purity.dao;

import cn.mgl.purity.model.persistent.Menu;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 菜单模块数据持久层
 */

public interface MenuDao {
    int insert(Menu menu);

    int update(Menu menu);

    Menu getById(Long id);

    Menu getByName(String name);

    List<Menu> getAll();

    int delete(Long id);

    List<Menu> getSibling(Menu menu);

    List<Menu> getUserMenus(Long userId);
}
