package com.webank.webase.front.config;

import com.webank.webase.front.account.MyUserDetailsService;
import com.webank.webase.front.config.security.JsonAuthenticationEntryPoint;
import com.webank.webase.front.config.security.JsonLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
    @Autowired
    private JsonAuthenticationEntryPoint jsonAuthenticationEntryPoint;
    @Autowired
    private MyUserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
        http
                .authorizeRequests()
                .antMatchers("/global/**","/static/**","/console/**","/user/register","/websocket/*").permitAll()
              //  .antMatchers("/account/login","/login", "/index*").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable() // close csrf
                .httpBasic().authenticationEntryPoint(jsonAuthenticationEntryPoint).and()
               // .exceptionHandling().authenticationEntryPoint( jsonAuthenticationEntryPoint).and()
                .formLogin()
                .loginPage("/index.html").loginProcessingUrl("/account/login")
                .usernameParameter("account").passwordParameter("accountPwd").permitAll()
                .successHandler(loginSuccessHandler) // if login success
                .failureHandler(loginFailHandler)
                .and()
                .sessionManagement().maximumSessions(1).and().invalidSessionUrl("/index.html")
                .and()
                .logout()
                .logoutUrl("/account/logout")
                .logoutSuccessHandler(logoutSuccessHandler)
                .permitAll();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(userAuthenticationProvider());
    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//    auth.inMemoryAuthentication()
//    .withUser("admin").password("admin").roles("ADMIN").and()
//    .withUser("user").password("user").roles("USER");
//  }

  @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    @Bean
    public DaoAuthenticationProvider userAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

}
