package cn.mgl.purity.service;


import cn.mgl.purity.dao.TestDao;
import cn.mgl.purity.model.persistent.MyTest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BService {
    final private TestService service;
    final private TestDao testDao;

    public BService(TestService service, TestDao testDao) {
        this.service = service;
        this.testDao = testDao;
    }

    @Transactional
    public void fuck(){
        MyTest myTest = new MyTest().setName("shawanyi");
        testDao.insert(myTest);
        myTest.setId(2L);
        service.update(myTest);
//        int m = 1/0;
    }
}
