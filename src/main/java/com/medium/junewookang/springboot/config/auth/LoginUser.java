package com.medium.junewookang.springboot.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER) // 메소드 파라미터에 붙이는 용도로 쓰겠다.
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUser { // 어노테이션 이름은 @LoginUser로 하겠다.
}
