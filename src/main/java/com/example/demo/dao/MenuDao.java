package com.example.demo.dao;

import com.example.demo.model.persistent.Menu;
import com.example.demo.model.service.MenuDto;
import com.example.demo.utils.MappingUtils;
import lombok.val;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 菜单模块数据持久层
 */
@Repository
public interface MenuDao {
     Menu getMenuById(Long id);

     Menu getMenuByName(String name);

     int createMenu(Menu menu);

     List<Menu> getAll();

     int updateMenu(Menu menu);

     int deleteMenu(Long id);

     List<Menu> getSibling(Menu menu);
     List<Menu> getUserMenus(Long userId);
}
