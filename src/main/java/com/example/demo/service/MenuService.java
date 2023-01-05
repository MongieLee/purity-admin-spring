package com.example.demo.service;

import com.example.demo.converter.p2s.MenuP2SConverter;
import com.example.demo.dao.MenuDao;
import com.example.demo.model.persistent.Menu;
import com.example.demo.model.service.MenuDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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
        MenuDto byId = findById(id);
        if (byId == null) {
            throw new RuntimeException("菜单不存在");
        }
        if (!menu.getParentId().equals(byId.getParentId())) {
            List<Menu> sibling = menuDao.getSibling(menu);
            Integer sequence = 0;
            if (sibling.size() > 0) {
                for (Menu item : sibling) {
                    if (item.getSequence() >= sequence) {
                        sequence = item.getSequence() + 1;
                    }
                }
            }
            menu.setSequence(sequence);
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
            throw new RuntimeException("删除失败，请先删除【" + menuById.getName() + "】菜单下的子级菜单");
        }
        menuDao.deleteMenu(id);
    }

    public MenuDto findById(Long id) {
        return menuP2SConverter.convert(menuDao.getMenuById(id));
    }

    public MenuDto createMenu(Menu menu) {
        List<Menu> sibling = menuDao.getSibling(menu);
        Integer sequence = 0;
        if (sibling.size() > 0) {
            for (Menu item : sibling) {
                if (item.getSequence() >= sequence) {
                    sequence = item.getSequence() + 1;
                }
            }
        }
        menu.setSequence(sequence);
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
                .map(menuP2SConverter::convert)
                .collect(Collectors.toList());

        for (MenuDto menu : menuList) {
            if (menu.getId() == id) {
                result = menu;
            }
            for (MenuDto m : menuList) {
                if (menu.getId() == m.getParentId()) {
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
                .map(menuP2SConverter::convert)
                .collect(Collectors.toList());
        return convertTree(collect);
    }

    private List<MenuDto> convertTree(List<MenuDto> menuList) {
        List<MenuDto> collect = getAllMenu().stream().map(menuP2SConverter::convert).collect(Collectors.toList());
        List<MenuDto> treeResult = new ArrayList<>();
        for (MenuDto menu : collect) {
            for (MenuDto m : menuList) {
                if (Objects.equals(menu.getId(), m.getParentId())) {
                    m.setParentName(menu.getName());
                    menu.getChildren().add(m);
                }
            }
            if (menu.getChildren().size() != 0) {
                menu.getChildren().sort(Comparator.comparingInt(MenuDto::getSequence));
            }
            if (menu.getParentId() == null &&
                    (menuList.stream().anyMatch(i -> i.getId().equals(menu.getId())) ||
                            menuList.stream().anyMatch(i -> i.getParentId().equals(menu.getId())))
            ) {
                treeResult.add(menu);
            }
        }
        treeResult.sort((Comparator.comparingInt(MenuDto::getSequence)));
        return treeResult;
    }

    public List<Menu> getSibling(Menu menu) {
        return menuDao.getSibling(menu);
    }

    public void moveUp(Menu menu) {
        List<Menu> sibling = menuDao.getSibling(menu);
        sibling.sort(Comparator.comparingInt(Menu::getSequence));
        if (Objects.equals(sibling.get(0).getId(), menu.getId())) {
            throw new RuntimeException("当前菜单层级中，【" + menu.getName() + "】已是第一位");
        }
        Integer target = null;
        Menu replace = null;
        for (int i = 0; i < sibling.size(); i++) {
            Menu record = sibling.get(i);
            if (record.getId() == menu.getId()) {
                target = menu.getSequence();
                replace = sibling.get(i - 1);
            }
        }
        menu.setSequence(Objects.requireNonNull(replace).getSequence());
        replace.setSequence(target);
        menuDao.updateMenu(replace);
        menuDao.updateMenu(menu);
    }

    public void moveDown(Menu menu) {
        List<Menu> sibling = menuDao.getSibling(menu);
        sibling.sort((o1, o2) -> o2.getSequence() - o1.getSequence());
        if (sibling.get(0).getId() == menu.getId()) {
            throw new RuntimeException("当前菜单层级中，【" + menu.getName() + "】已是最后一位");
        }
        Integer target = null;
        Menu replace = null;
        for (int i = 0; i < sibling.size(); i++) {
            Menu record = sibling.get(i);
            if (record.getId() == menu.getId()) {
                target = menu.getSequence();
                replace = sibling.get(i - 1);
            }
        }
        menu.setSequence(Objects.requireNonNull(replace).getSequence());
        replace.setSequence(target);
        menuDao.updateMenu(replace);
        menuDao.updateMenu(menu);
    }

    public void moveToStar(Menu menu) {
        List<Menu> sibling = menuDao.getSibling(menu);
        sibling.sort(Comparator.comparingInt(Menu::getSequence));
        Menu first = sibling.get(0);
        if (first.getId() == menu.getId()) {
            throw new RuntimeException("当前菜单层级中，【" + menu.getName() + "】已是第一位");
        }
        menuDao.updateMenu(menu.setSequence(first.getSequence() - 1));

    }

    public void moveToEnd(Menu menu) {
        List<Menu> sibling = menuDao.getSibling(menu);
        sibling.sort(Comparator.comparingInt(Menu::getSequence));
        Collections.reverse(sibling);
        Menu first = sibling.get(0);
        if (first.getId() == menu.getId()) {
            throw new RuntimeException("当前菜单层级中，【" + menu.getName() + "】已是最后一位");
        }
        menuDao.updateMenu(menu.setSequence(first.getSequence() + 1));
    }

    public void test() {
        menuDao.test();
    }

    public List<MenuDto> getUserMenus(Long userId) {
        return convertTree(menuDao.getUserMenus(userId).stream()
                .map(menuP2SConverter::convert)
                .collect(Collectors.toList()));
    }
}
