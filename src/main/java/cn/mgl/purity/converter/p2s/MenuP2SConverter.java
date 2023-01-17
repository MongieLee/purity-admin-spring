package cn.mgl.purity.converter.p2s;

import cn.mgl.purity.model.persistent.Menu;
import cn.mgl.purity.model.service.MenuDto;
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
                .setIsLink(menu.getIsLink())
                .setParentId(menu.getParentId())
                .setIcon(menu.getIcon())
                .setVisible(menu.getVisible())
                .setMenuType(menu.getMenuType())
                .setRemark(menu.getRemark())
                .setCompName(menu.getCompName())
                .setVisible(menu.getVisible())
                .setState(menu.getState())
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
                .setIsLink(menuDto.getIsLink())
                .setCompName(menuDto.getCompName())
                .setIcon(menuDto.getIcon())
                .setVisible(menuDto.getVisible())
                .setState(menuDto.getState())
                .setMenuType(menuDto.getMenuType())
                .setRemark(menuDto.getRemark())
                .setPermission(menuDto.getPermission())
                .setSequence(menuDto.getSequence());
    }
}
