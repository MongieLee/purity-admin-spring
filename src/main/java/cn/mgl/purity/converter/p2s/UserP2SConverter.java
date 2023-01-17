package cn.mgl.purity.converter.p2s;

import cn.mgl.purity.model.persistent.User;
import cn.mgl.purity.model.persistent.UserDto;
import com.google.common.base.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserP2SConverter extends Converter<User, UserDto> {

    @Override
    protected UserDto doForward(User user) {
        return new UserDto()
                .setId(user.getId())
                .setUsername(user.getUsername())
                .setNickname(user.getNickname())
                .setAvatar(user.getAvatar())
                .setStatus(user.getStatus())
//                .setUserType(user.getUserType())
                .setCreatedAt(user.getCreatedAt())
                .setUpdatedAt(user.getUpdatedAt());
    }

    @Override
    protected User doBackward(UserDto userDto) {
        return new User()
                .setId(userDto.getId())
                .setUsername(userDto.getUsername())
                .setNickname(userDto.getNickname())
                .setAvatar(userDto.getAvatar())
                .setStatus(userDto.getStatus())
//                .setUserType(userDto.getUserType())
                .setCreatedAt(userDto.getCreatedAt())
                .setUpdatedAt(userDto.getUpdatedAt());
    }
}
