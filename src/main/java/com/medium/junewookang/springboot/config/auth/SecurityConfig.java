package com.medium.junewookang.springboot.config.auth;

import com.medium.junewookang.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity // security 설정 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http // 와따 뭐이렇게 길게 점으로 메소드들이 이어져있다냐
            .csrf().disable()
            .headers().frameOptions().disable() // h2-console을 사용하기 위해 disable하는거라고 함.
            .and()
                .authorizeRequests()
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll() // 모두 접근가능
                .antMatchers("/api/v1/**").hasRole(Role.USER.name()) // 유저만 접근가능
                .anyRequest().authenticated() // 나머지는 로그인한 사용자들에게만 허용하게 함.
            .and()
                .logout().logoutSuccessUrl("/")
            .and()
                .oauth2Login().userInfoEndpoint().userService(customOAuth2UserService);


    }
}
