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
                .setChildren(new ArrayList<>())
                .setSequence(menu.getSequence())
                .setCreatedAt(menu.getCreatedAt())
                .setUpdatedAt(menu.getUpdatedAt());
    }

    @Override
    protected Menu doBackward(MenuDto menuDto) {
        return new Menu()
                .setId(menuDto.getId())
                .setName(menuDto.getName())
                .setPath(menuDto.getPath())
                .setParentId(menuDto.getParentId())
                .setSequence(menuDto.getSequence())
                .setCreatedAt(menuDto.getCreatedAt())
                .setUpdatedAt(menuDto.getUpdatedAt());
    }
}
