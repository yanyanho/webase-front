package com.webank.webase.front.account;

import com.webank.webase.front.base.exception.FrontException;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public User addAccountInfo(User user) {

       User user1 = userRepository.findByUsername(user.getUsername());

       if(user1!=null) {

           throw new FrontException("用户名已存在");
        }

        String password =   user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }


}