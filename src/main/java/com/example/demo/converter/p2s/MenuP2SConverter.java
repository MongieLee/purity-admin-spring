package com.example.demo.converter.p2s;

import com.example.demo.model.persistent.Menu;
import com.example.demo.model.service.MenuDto;
import com.google.common.base.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Menu实体 persistence层与service层转换
 */
@Component
public class MenuP2SConverter extends Converter<Menu, MenuDto> {
    @Override
    protected MenuDto doForward(Menu menu) {
        return new MenuDto()
                .setId(menu.getId())
                .setName(menu.getName())
                .setPath(menu.getPath())
                .setParentId(menu.getParentId())
                .setIcon(menu.getIcon())
                .setVisible(menu.getVisible())
                .setMenuType(menu.getMenuType())
                .setRemark(menu.getRemark())
                .setPermission(menu.getPermission())
                .setSequence(menu.getSequence())
                .setChildren(new ArrayList<>())
                .setCreatedAt(menu.getCreatedAt());
    }

    @Override
    protected Menu doBackward(MenuDto menuDto) {
        return new Menu()
                .setId(menuDto.getId())
                .setName(menuDto.getName())
                .setPath(menuDto.getPath())
                .setParentId(menuDto.getParentId())
                .setIcon(menuDto.getIcon())
                .setVisible(menuDto.getVisible())
                .setMenuType(menuDto.getMenuType())
                .setRemark(menuDto.getRemark())
                .setPermission(menuDto.getPermission())
                .setSequence(menuDto.getSequence());
    }
}
