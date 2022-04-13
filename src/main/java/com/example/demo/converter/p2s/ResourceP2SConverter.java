package com.example.demo.converter.p2s;

import com.example.demo.model.persistent.Resource;
import com.example.demo.model.service.ResourceDto;
import com.google.common.base.Converter;
import org.springframework.stereotype.Component;

@Component
public class ResourceP2SConverter extends Converter<Resource, ResourceDto> {

    @Override
    protected ResourceDto doForward(Resource resource) {
        return new ResourceDto()
                .setId(resource.getId())
                .setName(resource.getName())
                .setUrl(resource.getUrl())
                .setDescription(resource.getDescription())
                .setCategroyId(resource.getCategroyId())
                .setCreatedAt(resource.getCreatedAt())
                .setUpdatedAt(resource.getUpdatedAt());
    }

    @Override
    protected Resource doBackward(ResourceDto resourceDto) {
        return new Resource()
                .setId(resourceDto.getId())
                .setName(resourceDto.getName())
                .setUrl(resourceDto.getUrl())
                .setDescription(resourceDto.getDescription())
                .setCategroyId(resourceDto.getCategroyId())
                .setCreatedAt(resourceDto.getCreatedAt())
                .setUpdatedAt(resourceDto.getUpdatedAt());
    }
}
