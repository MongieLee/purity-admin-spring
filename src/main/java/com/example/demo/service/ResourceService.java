package com.example.demo.service;

import com.example.demo.converter.p2s.ResourceP2SConverter;
import com.example.demo.dao.MenuDao;
import com.example.demo.dao.ResourceCategroyDao;
import com.example.demo.dao.ResourceDao;
import com.example.demo.model.persistent.Resource;
import com.example.demo.model.service.ResourceDto;
import com.github.pagehelper.PageHelper;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class ResourceService {

    private final ResourceDao resourceDao;
    private final MenuDao menuDao;
    private final ResourceCategroyDao resourceCategroyDao;
    private final ResourceP2SConverter resourceP2SConverter;

    public ResourceService(ResourceDao ResourceDao, MenuDao menuDao, ResourceCategroyDao resourceCategroyDao, ResourceP2SConverter resourceP2SConverter) {
        this.resourceDao = ResourceDao;
        this.menuDao = menuDao;
        this.resourceCategroyDao = resourceCategroyDao;
        this.resourceP2SConverter = resourceP2SConverter;
    }

    public List<Resource> getAllResource() {
        return resourceDao.getAll();
    }

    public void createResource(Resource resource) {
        resourceDao.createResource(resource);
    }

    public ResourceDto getResourceByName(String name) {
        return resourceP2SConverter.convert(resourceDao.getResourceByName(name));
    }

    public List<ResourceDto> getList(ResourceDto resource, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        return resourceDao.getList(resource);
    }

    public ResourceDto getResourceById(Long id) {
        val resourceById = resourceDao.getResourceById(id);
        if (resourceById == null) {
            throw new RuntimeException("id为【" + id + "】的资源不存在，请检查传递参数");
        }
        val result = resourceP2SConverter.convert(resourceById);
        val categroyId = result.getCategroyId();
        if (categroyId != null) {
            val resourceCategroyById = resourceCategroyDao.getResourceCategroyById(categroyId);
            result.setCategroyName(resourceCategroyById.getName());
        }
        return result;
    }

    public ResourceDto updateResource(Resource resource) {
        resourceDao.updateResource(resource);
        return resourceP2SConverter.convert(resourceDao.getResourceById(resource.getId()));
    }

    public void deleteResource(Long id) {
        val resourceById = resourceDao.getResourceById(id);
        if (resourceById == null) {
            throw new RuntimeException("删除失败，id为【" + id + "】的资源不存在");
        }
        resourceDao.deleteResource(id);
    }
}
