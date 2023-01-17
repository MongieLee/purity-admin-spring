package cn.mgl.purity.dao;

import cn.mgl.purity.model.persistent.MyTest;

import java.util.List;

public interface TestDao {
    int insert(MyTest obj);

    int delete(Long id);

    int update(MyTest obj);

    MyTest getById(Long id);

    MyTest getByName(String name);

    List<MyTest> getAll();
}
