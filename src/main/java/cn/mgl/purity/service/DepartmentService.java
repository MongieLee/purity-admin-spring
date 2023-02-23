package cn.mgl.purity.service;

import cn.mgl.purity.converter.p2s.DepartmentP2SConverter;
import cn.mgl.purity.dao.DepartmentDao;
import cn.mgl.purity.exception.ServiceBusinessException;
import cn.mgl.purity.model.persistent.Department;
import cn.mgl.purity.model.persistent.DepartmentDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class DepartmentService {
    private final DepartmentDao departmentDao;
    private final DepartmentP2SConverter departmentP2SConverter;

    public DepartmentService(DepartmentDao departmentDao, DepartmentP2SConverter departmentP2SConverter) {
        this.departmentDao = departmentDao;
        this.departmentP2SConverter = departmentP2SConverter;
    }

    public Department insert(Department dep) {
        Department byName = getByName(dep.getName());
        if (byName != null) {
            throw new ServiceBusinessException("该部门已存在");
        }
        departmentDao.insert(dep);
        return dep;
    }

    public void delete(Long id) {
        if (getById(id) == null) {
            throw new ServiceBusinessException("该部门不存在");
        }
        departmentDao.delete(id);
    }

    /**
     * 更细部门
     *
     * @param dep 部门信息
     * @return 修改成功后的部门信息
     */
    @ApiOperation("更新部门")
    public Department update(Department dep) {
        if (null == getById(dep.getId())) {
            throw new ServiceBusinessException("该部门不存在");
        }
        departmentDao.update(dep);
        return getById(dep.getId());
    }

    public Department getById(Long id) {
        return departmentDao.getById(id);
    }

    public Department getByName(String name) {
        return departmentDao.getByName(name);
    }

    public List<Department> getAll() {
        return departmentDao.getAll();
    }

    public List<DepartmentDto> getTree() {
        List<DepartmentDto> allFromDb = departmentDao.getAll().stream().map(departmentP2SConverter::convert).collect(Collectors.toList());
        List<DepartmentDto> result = new ArrayList<>();
        allFromDb.forEach(depDto -> {
            allFromDb.forEach(dep -> {
                if (Objects.equals(depDto.getId(), dep.getParentId())) {
                    depDto.getChildren().add(dep);
                }
            });
            // 有子集则排序
            if (depDto.getChildren().size() != 0) {
                depDto.getChildren().sort(Comparator.comparingInt(DepartmentDto::getSequence));
            }
            if (depDto.getParentId() == null) {
                result.add(depDto);
            }
        });
        return result;
    }
}
