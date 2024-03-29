package cn.mgl.purity.converter.p2s;

import cn.mgl.purity.model.persistent.Department;
import cn.mgl.purity.model.persistent.DepartmentDto;
import com.google.common.base.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Menu实体 persistence层与service层转换
 */
@Component
public class DepartmentP2SConverter extends Converter<Department, DepartmentDto> {
    @Override
    protected DepartmentDto doForward(Department dep) {
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto
                .setName(dep.getName())
                .setParentId(dep.getParentId())
                .setPrincipal(dep.getPrincipal())
                .setPhoneNumber(dep.getPhoneNumber())
                .setSequence(dep.getSequence())
                .setState(dep.getState())
                .setId(dep.getId())
                .setCreatedAt(dep.getCreatedAt())
                .setUpdatedAt(dep.getUpdatedAt());
        departmentDto.setChildren(new ArrayList<>());
        return departmentDto;
    }

    @Override
    protected Department doBackward(DepartmentDto depDto) {
        Department dep = new Department();
        dep
                .setName(dep.getName())
                .setParentId(dep.getParentId())
                .setPrincipal(dep.getPrincipal())
                .setPhoneNumber(dep.getPhoneNumber())
                .setSequence(dep.getSequence())
                .setState(dep.getState())
                .setId(dep.getId())
                .setCreatedAt(dep.getCreatedAt())
                .setUpdatedAt(dep.getUpdatedAt());
        return dep;
    }
}
