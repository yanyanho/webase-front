/*
 * Copyright 2014-2019  the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.webank.webase.front.config.security;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.webank.webase.front.base.JsonUtils.toJSONString;

@Log4j2
@Component("loginSuccessHandler")
public class LoginSuccessHandler implements AuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {
        log.debug("login success");

//        BaseResponse baseResponse = new BaseResponse(ConstantCode.SUCCESS);
//        baseResponse.setData(rsp);
       // Object user = request.getHeaders("account");
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String backStr = toJSONString(user);
        log.debug("login backInfo:{}", backStr);

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(backStr);
    }

}
