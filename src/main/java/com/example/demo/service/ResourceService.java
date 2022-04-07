package com.example.demo.service;

import com.example.demo.converter.p2s.ResourceP2SConverter;
import com.example.demo.dao.MenuDao;
import com.example.demo.dao.ResourceDao;
import com.example.demo.model.presistent.Resource;
import com.example.demo.model.service.ResourceDto;
import com.github.pagehelper.PageHelper;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class ResourceService {

    private final ResourceDao resourceDao;
    private final MenuDao menuDao;
    private final ResourceP2SConverter resourceP2SConverter;

    public ResourceService(ResourceDao ResourceDao, MenuDao menuDao, ResourceP2SConverter resourceP2SConverter) {
        this.resourceDao = ResourceDao;
        this.menuDao = menuDao;
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

    public List<ResourceDto> getList(Resource resource, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<ResourceDto> collect = resourceDao.getList(resource).stream()
                .map(item -> {
                    val convert = resourceP2SConverter.convert(item);
                    val menuById = menuDao.getMenuById(convert.getCategroyId());
                    convert.setCategroyName(menuById.getName());
                    return convert;
                }).collect(Collectors.toList());
        return collect;
    }

    public ResourceDto getResourceById(Long id) {
        val resourceById = resourceDao.getResourceById(id);
        if (resourceById == null) {
            throw new RuntimeException("id为【" + id + "】的资源不存在，请检查传递参数");
        }
        val result = resourceP2SConverter.convert(resourceById);
        val categroyId = result.getCategroyId();
        if (categroyId != null) {
            val menuById = menuDao.getMenuById(categroyId);
            result.setCategroyName(menuById.getName());
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

//
//    public ResourceDto updateResource(Long id, Resource Resource) {
//        ResourceDto byId = findById(id);
//        if (byId == null) {
//            throw new RuntimeException("菜单不存在");
//        }
//        if (Resource.getParentId() != byId.getParentId()) {
//            List<Resource> sibling = ResourceDao.getSibling(Resource);
//            Integer sequence = 0;
//            if (sibling.size() > 0) {
//                for (Resource item : sibling) {
//                    if (item.getSequence() >= sequence) {
//                        sequence = item.getSequence() + 1;
//                    }
//                }
//            }
//            Resource.setSequence(0 == sequence ? 0 : sequence);
//        }
//        ResourceDao.updateResource(Resource.setId(id));
//        return getResourceById(Resource.getId());
//    }
//
//    public void deleteResource(Long id) {
//        ResourceDto ResourceById = this.getResourceById(id);
//        if (ResourceById.getId() == null) {
//            throw new RuntimeException("菜单不存在");
//        }
//        if (ResourceById.getChildren().size() > 0) {
//            throw new RuntimeException("删除失败，请先删除【" + ResourceById.getName() + "】菜单下的子级菜单");
//        }
//        ResourceDao.deleteResource(id);
//    }
//
//    public ResourceDto findById(Long id) {
//        return ResourceP2SConverter.convert(ResourceDao.getResourceById(id));
//    }
//
//    public ResourceDto createResource(Resource Resource) {
//        List<Resource> sibling = ResourceDao.getSibling(Resource);
//        Integer sequence = 0;
//        if (sibling.size() > 0) {
//            for (Resource item : sibling) {
//                if (item.getSequence() >= sequence) {
//                    sequence = item.getSequence() + 1;
//                }
//            }
//        }
//        Resource.setSequence(0 == sequence ? 0 : sequence);
//        ResourceDao.createResource(Resource);
//        return findById(Resource.getId());
//    }
//
//    public ResourceDto getResourceById(Long id) {
//        if (this.findById(id) == null) {
//            throw new RuntimeException("菜单不存在");
//        }
//        ResourceDto result = null;
//        List<ResourceDto> ResourceList = ResourceDao.getAll()
//                .stream()
//                .map(Resource -> ResourceP2SConverter.convert(Resource))
//                .collect(Collectors.toList());
//
//        for (ResourceDto Resource : ResourceList) {
//            if (Resource.getId() == id) {
//                result = Resource;
//            }
//            for (ResourceDto m : ResourceList) {
//                if (Resource.getId() == m.getParentId()) {
//                    Resource.getChildren().add(m);
//                }
//            }
//        }
//        return result;
//    }
//
//    public ResourceDto getResourceByName(String name) {
//        return ResourceP2SConverter.convert(ResourceDao.getResourceByName(name));
//    }
//
//    public List<ResourceDto> tree() {
//        List<ResourceDto> collect = ResourceDao.getAll()
//                .stream()
//                .map(Resource -> ResourceP2SConverter.convert(Resource))
//                .collect(Collectors.toList());
//        return convertTree(collect);
//    }
//
//    private List<ResourceDto> convertTree(List<ResourceDto> ResourceList) {
//        List<ResourceDto> treeResult = new ArrayList<>();
//        for (ResourceDto Resource : ResourceList) {
//            for (ResourceDto m : ResourceList) {
//                if (Resource.getId() == m.getParentId()) {
//                    m.setParentName(Resource.getName());
//                    Resource.getChildren().add(m);
//                }
//            }
//            if (Resource.getChildren().size() != 0) {
//                Collections.sort(Resource.getChildren(), Comparator.comparingInt(ResourceDto::getSequence));
//            }
//            if (Resource.getParentId() == null) {
//                treeResult.add(Resource);
//            }
//        }
//        treeResult.sort((Comparator.comparingInt(ResourceDto::getSequence)));
//        return treeResult;
//    }
//
//    public List<Resource> getSibling(Resource Resource) {
//        return ResourceDao.getSibling(Resource);
//    }
//
//    public void moveUp(Resource Resource) {
//        List<Resource> sibling = ResourceDao.getSibling(Resource);
//        sibling.sort(Comparator.comparingInt(Resource::getSequence));
//        if (sibling.get(0).getId() == Resource.getId()) {
//            throw new RuntimeException("当前菜单层级中，【" + Resource.getName() + "】已是第一位");
//        }
//        Integer target = null;
//        Resource replace = null;
//        for (int i = 0; i < sibling.size(); i++) {
//            Resource record = sibling.get(i);
//            if (record.getId() == Resource.getId()) {
//                target = Resource.getSequence();
//                replace = sibling.get(i - 1);
//            }
//        }
//        Resource.setSequence(Objects.requireNonNull(replace).getSequence());
//        replace.setSequence(target);
//        ResourceDao.updateResource(replace);
//        ResourceDao.updateResource(Resource);
//    }
//
//    public void moveDown(Resource Resource) {
//        List<Resource> sibling = ResourceDao.getSibling(Resource);
//        sibling.sort((o1, o2) -> o2.getSequence() - o1.getSequence());
//        if (sibling.get(0).getId() == Resource.getId()) {
//            throw new RuntimeException("当前菜单层级中，【" + Resource.getName() + "】已是最后一位");
//        }
//        Integer target = null;
//        Resource replace = null;
//        for (int i = 0; i < sibling.size(); i++) {
//            Resource record = sibling.get(i);
//            if (record.getId() == Resource.getId()) {
//                target = Resource.getSequence();
//                replace = sibling.get(i - 1);
//            }
//        }
//        Resource.setSequence(Objects.requireNonNull(replace).getSequence());
//        replace.setSequence(target);
//        ResourceDao.updateResource(replace);
//        ResourceDao.updateResource(Resource);
//    }
//
//    public void moveToStar(Resource Resource) {
//        List<Resource> sibling = ResourceDao.getSibling(Resource);
//        sibling.sort(Comparator.comparingInt(Resource::getSequence));
//        Resource first = sibling.get(0);
//        if (first.getId() == Resource.getId()) {
//            throw new RuntimeException("当前菜单层级中，【" + Resource.getName() + "】已是第一位");
//        }
//        ResourceDao.updateResource(Resource.setSequence(first.getSequence() - 1));
//
//    }
//
//    public void moveToEnd(Resource Resource) {
//        List<Resource> sibling = ResourceDao.getSibling(Resource);
//        sibling.sort(Comparator.comparingInt(Resource::getSequence));
//        Collections.reverse(sibling);
//        Resource first = sibling.get(0);
//        if (first.getId() == Resource.getId()) {
//            throw new RuntimeException("当前菜单层级中，【" + Resource.getName() + "】已是最后一位");
//        }
//        ResourceDao.updateResource(Resource.setSequence(first.getSequence() + 1));
//    }
}
