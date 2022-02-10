package com.example.demo.service;

import com.example.demo.dao.UserDao;
import com.example.demo.entity.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserDao userDao, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDao = userDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User getUserByName(String username) {
        return userDao.findUserByUsername(username);
    }

    @Override
    public User login(User user) {
        User targetUser = getUserByName(user.getUsername());
        if (targetUser == null) {
            throw new UsernameNotFoundException(user.getUsername() + "不存在");
        }
        return user;
    }

    @Override
    public void register(String username, String encryptedPassword) {
        User registerUser = User.builder()
                .username(username)
                .encryptedPassword(bCryptPasswordEncoder.encode(encryptedPassword))
                .build();
        userDao.register(registerUser);
    }
}