package cn.mgl.purity.service;

import cn.mgl.purity.dao.TestDao;
import cn.mgl.purity.model.persistent.MyTest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
public class TestService {
    final private TestDao testDao;

    public TestService(TestDao testDao) {
        this.testDao = testDao;
    }

    @Transactional
    public int insert(MyTest obj) {
        int insert = testDao.insert(obj);
        update(obj);
        int b = 1/0;
        return insert;
    }

    @Transactional(propagation = Propagation.NESTED)
    public int update(MyTest obj) {
        testDao.update(obj.setName("今晚打老虎"+ new Random().nextInt()).setId(2L));
                int m = 1/0;
        return 1;
    }

    public int delete(Long id) {
        return testDao.delete(id);
    }

    public List<MyTest> getAll() {
        return testDao.getAll();
    }

    public MyTest getById(Long id) {
        return testDao.getById(id);
    }

    public MyTest getByName(String name) {
        return testDao.getByName(name);
    }
}
