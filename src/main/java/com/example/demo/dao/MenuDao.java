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

public interface MenuDao {
    int insert(Menu menu);

    int update(Menu menu);

    Menu getById(Long id);

    Menu getByName(String name);

    List<Menu> getAll();

    int delete(Long id);

    int findMaxSequence();

    List<Menu> getSibling(Menu menu);

    List<Menu> getUserMenus(Long userId);
}
