package com.medium.junewookang.springboot.config.auth;

import com.medium.junewookang.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity // Spring Security 설정에 붙여주는 어노테이션
public class SecurityConfig extends WebSecurityConfigurerAdapter { // 음.. 일단 spring security 설정같은 건가보다.

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override // protected인걸 보아 SecurityConfig도 어떠한 클래스에 상속되어 사용되나 보다.
    protected void configure(HttpSecurity http) throws Exception {
        // 아니 이렇게 길게 체이닝할거면 차라리 json이나 yaml로 설정파일을 만드는게 낫지 않나..? 싶다.
        http
                // csrf 경고 없애주는것같고 (h2 console 사용 위함)
                .csrf().disable()
                // 헤더의 무슨 옵션을 껐네 (이 또한 h2 console 사용 위함)
                .headers().frameOptions().disable()


                .and()
                // antMatchers를 통해 권한을 관리할건데
                .authorizeRequests()
                // 웹페이지, DB콘솔, 정적파일은 로그인한&로그인하지 않은 모든 사용자에게 접근을 허용할 것이고
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
                // api는 USER 권한을 가진 로그인한 사람만 접근 가능하도록 할것이며
                .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                // 명세하지 않은 경로들에 대해선, 로그인한 사람만 접근 가능하도록 할 것이다.
                .anyRequest().authenticated()


                .and()
                // 로그아웃 시 설정을 관리할건데
                .logout()
                // 로그아웃이 설정할 경우, 홈으로 리다이렉트할 것이다.
                .logoutSuccessUrl("/")


                .and()
                // OAuth2로그인, 즉 소셜로그인에 대한 설정을 할건데
                .oauth2Login()
                // 그 중에서도 소셜로그인 성공시 설정을 할 것이다.
                .userInfoEndpoint()
                // 소셜로그인 성공 시, customOAuth2UserService가 후속조치를 하도록 설정하겠다. -> 직접 짜야겠지!
                .userService(customOAuth2UserService);
    }
}
