package com.webank.webase.front.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationSuccessHandler loginSuccessHandler;
    @Autowired
    private AuthenticationFailureHandler loginFailHandler;
    @Autowired
    private JsonLogoutSuccessHandler logoutSuccessHandler;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/account/login","/login", "/home").permitAll()
                .anyRequest().authenticated()
                .and().csrf()
                .disable() // close csrf
                .formLogin()
                .loginPage("/login").loginProcessingUrl("/account/login")
                .usernameParameter("account").passwordParameter("accountPwd").permitAll()
                .successHandler(loginSuccessHandler) // if login success
                .failureHandler(loginFailHandler)
                .and()
                .logout()
                .logoutUrl("/account/logout")
                .logoutSuccessHandler(logoutSuccessHandler)
                .permitAll();
    }

//    @Bean
//    @Override
//    public UserDetailsService userDetailsService() {
//        UserDetails user =
//                User.withUsername("admin")
//                        .password("admin")
//                        .roles("USER")
//                        .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }


    @Autowired
public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication()
    .withUser("admin").password("admin").roles("ADMIN").and()
    .withUser("user").password("user").roles("USER");
  }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(userAuthenticationProvider());
//    }

    @Bean("bCryptPasswordEncoder")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public DaoAuthenticationProvider userAuthenticationProvider(){
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setUserDetailsService(userDetailService);
//        daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
//        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
//        return daoAuthenticationProvider;
//    }

}
