package com.example.demo.service;

import com.example.demo.converter.p2s.MenuP2SConverter;
import com.example.demo.dao.MenuDao;
import com.example.demo.model.presistent.Menu;
import com.example.demo.model.service.MenuDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class MenuService {

    private final MenuDao menuDao;
    private final MenuP2SConverter menuP2SConverter;

    public MenuService(MenuDao menuDao, MenuP2SConverter menuP2SConverter) {
        this.menuDao = menuDao;
        this.menuP2SConverter = menuP2SConverter;
    }

    public List<Menu> getMenu(Long id) {
        return null;
    }

    public List<Menu> getAllMenu() {
        return menuDao.getAll();
    }

    public MenuDto updateMenu(Long id, Menu menu) {
        if (findById(id) == null) {
            throw new RuntimeException("菜单不存在");
        }
        menuDao.updateMenu(menu.setId(id));
        return getMenuById(menu.getId());
    }

    public void deleteMenu(Long id) {
        MenuDto menuById = this.getMenuById(id);
        if (menuById.getId() == null) {
            throw new RuntimeException("菜单不存在");
        }
        if (menuById.getChildren().size() > 0) {
            throw new RuntimeException("删除失败，请先删除该菜单下的子级菜单");
        }
        menuDao.deleteMenu(id);
    }

    public MenuDto findById(Long id) {
        return menuP2SConverter.convert(menuDao.getMenuById(id));
    }

    public MenuDto createMenu(Menu menu) {
        menu.setSequence(menuDao.findMaxSequence() + 1);
        menuDao.createMenu(menu);
        return findById(menu.getId());
    }

    public MenuDto getMenuById(Long id) {
        if (this.findById(id) == null) {
            throw new RuntimeException("菜单不存在");
        }
        MenuDto result = null;
        List<MenuDto> menuList = menuDao.getAll()
                .stream()
                .map(menu -> menuP2SConverter.convert(menu))
                .collect(Collectors.toList());

        for (MenuDto menu : menuList) {
            if (menu.getId() == id) {
                result = menu;
            }
            for (MenuDto m : menuList) {
                if (menu.getId() == m.getPId()) {
                    menu.getChildren().add(m);
                }
            }
        }
        return result;
    }

    public MenuDto getMenuByName(String name) {
        return menuP2SConverter.convert(menuDao.getMenuByName(name));
    }

    public List<MenuDto> tree() {
        List<MenuDto> collect = menuDao.getAll()
                .stream()
                .map(menu -> menuP2SConverter.convert(menu))
                .collect(Collectors.toList());
        return convertTree(collect);
    }

    private List<MenuDto> convertTree(List<MenuDto> menuList) {
        List<MenuDto> treeResult = new ArrayList<>();
        for (MenuDto menu : menuList) {
            for (MenuDto m : menuList) {
                if (menu.getId() == m.getPId()) {
                    m.setParentName(menu.getName());
                    menu.getChildren().add(m);
                }
            }
            if (menu.getPId() == null) {
                treeResult.add(menu);
            }
        }
        return treeResult;
    }
}
