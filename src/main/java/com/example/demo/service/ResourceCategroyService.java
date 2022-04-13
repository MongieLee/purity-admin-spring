package com.example.demo.service;

import com.example.demo.dao.ResourceCategroyDao;
import com.example.demo.model.persistent.ResourceCategroy;
import com.github.pagehelper.PageHelper;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class ResourceCategroyService {

    private final ResourceCategroyDao resourceCategroyDao;

    public ResourceCategroyService(ResourceCategroyDao resourceCategroyDao) {
        this.resourceCategroyDao = resourceCategroyDao;
    }

    public List<ResourceCategroy> getAllResourceCategroy() {
        return resourceCategroyDao.getAll();
    }

    public void createResourceCategroy(ResourceCategroy resourceCategroy) {
        resourceCategroyDao.createResourceCategroy(resourceCategroy);
    }

    public ResourceCategroy getResourceCategroyByName(String name) {
        return resourceCategroyDao.getResourceCategroyByName(name);
    }

    public List<ResourceCategroy> getList(ResourceCategroy resourceCategroy, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        return resourceCategroyDao.getList(resourceCategroy);
    }

    public ResourceCategroy getResourceCategroyById(Long id) {
        val resourceCategroyById = resourceCategroyDao.getResourceCategroyById(id);
        if (resourceCategroyById == null) {
            throw new RuntimeException("id为【" + id + "】的分类不存在，请检查传递参数");
        }
        return resourceCategroyById;
    }

    public ResourceCategroy updateResourceCategroy(ResourceCategroy resourceCategroy) {
        resourceCategroyDao.updateResourceCategroy(resourceCategroy);
        return resourceCategroyDao.getResourceCategroyById(resourceCategroy.getId());
    }

    public void deleteResourceCategroy(Long id) {
        val resourceCategroyById = resourceCategroyDao.getResourceCategroyById(id);
        if (Objects.equals(resourceCategroyById, null)) {
            throw new RuntimeException("删除失败，id为【" + id + "】的分类不存在");
        }
        resourceCategroyDao.deleteResourceCategroy(id);
    }
}
