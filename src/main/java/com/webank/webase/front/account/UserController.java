package com.webank.webase.front.account;

import com.webank.webase.front.base.BaseResponse;
import com.webank.webase.front.base.ConstantCode;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.*;
import javax.validation.Valid;
import java.time.Duration;
import java.time.Instant;

@RestController
@RequestMapping(value = "user")
public class UserController {


    @Autowired
    UserService userService;


    /**
     * add account info.
     */
    @PostMapping(value = "/register")
  //  @PreAuthorize(ConstantProperties.HAS_ROLE_ADMIN)
    public BaseResponse addAccountInfo(@RequestBody @Valid User info, BindingResult result) {

        BaseResponse baseResponse = new BaseResponse(0);

        User user = userService.addAccountInfo(info);

        user.setPassword(null);
        baseResponse.setData(user);
        return baseResponse;
    }


}