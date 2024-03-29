package cn.mgl.purity.dao;

import cn.mgl.purity.model.persistent.Department;

import java.util.List;

/**
 * @author MongieLee
 * @version 1.0
 * @date 2022/12/1 13:25
 */
public interface DepartmentDao {
    int insert(Department dep);

    int update(Department dep);

    int delete(Long id);

    Department getById(Long id);

    Department getByName(String name);

    List<Department> getAll();
}
