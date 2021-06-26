package com.medium.junewookang.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing // Application.java에 있던거 여기로 옮겨옴. createdDate, modifiedDate 추가해주는거! @Configuration으로 되어 있으므로 WebMvcTest의 감시망을 피할 수 있다.
public class JpaConfig {
}
