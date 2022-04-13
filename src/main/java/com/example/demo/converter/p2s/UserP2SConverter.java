package com.example.demo.converter.p2s;

import com.example.demo.model.persistent.User;
import com.example.demo.model.persistent.UserDto;
import com.google.common.base.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserP2SConverter extends Converter<User, UserDto> {

    @Override
    protected UserDto doForward(User user) {
        return new UserDto()
                .setId(user.getId())
                .setUsername(user.getUsername())
                .setAvatar(user.getAvatar())
                .setStatus(user.getStatus())
                .setCreatedAt(user.getCreatedAt())
                .setUpdatedAt(user.getUpdatedAt());
    }

    @Override
    protected User doBackward(UserDto userDto) {
        return new User()
                .setId(userDto.getId())
                .setUsername(userDto.getUsername())
                .setAvatar(userDto.getAvatar())
                .setStatus(userDto.getStatus())
                .setCreatedAt(userDto.getCreatedAt())
                .setUpdatedAt(userDto.getUpdatedAt());
    }
}
