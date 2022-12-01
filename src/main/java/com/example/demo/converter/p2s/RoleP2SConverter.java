package com.example.demo.converter.p2s;

import com.example.demo.model.persistent.Role;
import com.example.demo.model.persistent.RoleDTO;
import com.google.common.base.Converter;

import java.util.ArrayList;

/**
 * @author MongieLee
 * @version 1.0
 * @date 2022/11/30 10:14
 */
public class RoleP2SConverter extends Converter<Role, RoleDTO> {
    @Override
    protected RoleDTO doForward(Role role) {
        return new RoleDTO()
                .setPermissionRoles(new ArrayList<>())
                .setId(role.getId())
                .setCreatedAt(role.getCreatedAt())
                .setUpdatedAt(role.getUpdatedAt())
                .setDescription(role.getDescription())
                .setName(role.getName())
                .setCode(role.getCode())
                .setState(role.getState());
    }

    @Override
    protected Role doBackward(RoleDTO roleDTO) {
        return new Role().
                setId(roleDTO.getId())
                .setCode(roleDTO.getCode())
                .setDescription(roleDTO.getDescription())
                .setState(roleDTO.getState())
                .setName(roleDTO.getName())
                .setCreatedAt(roleDTO.getCreatedAt())
                .setUpdatedAt(roleDTO.getUpdatedAt());
    }
}
