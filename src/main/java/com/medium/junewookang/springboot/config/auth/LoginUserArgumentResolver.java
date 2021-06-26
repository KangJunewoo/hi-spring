package com.medium.junewookang.springboot.config.auth;

import com.medium.junewookang.springboot.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;


/***
 * 직접 정의한 @LoginUser를 사용하기 위해 정의해놓은듯.
 */
@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {
    private final HttpSession httpSession;

    /**
     *
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 파라미터에 붙은 어노테이션이 @LoginUser인가?
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;

        // 파라미터(객체)의 클래스가 SessionUser인가?
        boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());

        // 즉, @LoginUser가 SessionUser 객체에 붙은 경우에 이 함수는 true를 리턴함.
        return isLoginUserAnnotation && isUserClass;
    }

    /**
     * 파라미터에 어떤 객체를 전달할 것인가?
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // 세션에 있는 user를 해당 파라미터에 주입할 것이다.
        return httpSession.getAttribute("user");
    }
}
