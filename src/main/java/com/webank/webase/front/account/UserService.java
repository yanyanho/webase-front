package com.webank.webase.front.account;

import com.webank.webase.front.base.exception.FrontException;
import com.webank.webase.front.keystore.KeyStoreService;
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
    @Autowired
    KeyStoreService keyStoreService;

    public User addAccountInfo(User user) {

       User user1 = userRepository.findByUsername(user.getUsername());

       if(user1!=null) {

           throw new FrontException("用户名已存在");
        }

        String password =   user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        user1 = userRepository.save(user);

        keyStoreService.createKeyStore(true,0,user.getUsername());

        return user1;
    }


}