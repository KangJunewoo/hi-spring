package com.medium.junewookang.springboot.config.auth;

import com.medium.junewookang.springboot.config.auth.dto.OAuthAttributes;
import com.medium.junewookang.springboot.config.auth.dto.SessionUser;
import com.medium.junewookang.springboot.domain.user.User;
import com.medium.junewookang.springboot.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;

import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

/***
 * 이 클래스는 SpringConfig에 사용되는 객체의 클래스로,
 * 소셜로그인 성공시 처리를 담당함.
 */
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    /***
     * 소셜로그인 성공시 호출하는 함수
     * @param userRequest (유저 정보가 담겨있는 요청)
     * @return 새 유저
     * @throws OAuth2AuthenticationException
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // delegate 영어사전 쳐보니 대리자라고 나옴. 음.. 인증을 처리하는 대리서비스? 정도라고 생각함녀 되려나.
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();

        // 대리서비스에 유저요청을 넣으면, 유저정보가 튀어나오겠지.
        OAuth2User oAuth2User = delegate.loadUser(userRequest);


        // 유저요청 자체에서 업체정보를 뽑아낸 후
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        // (직접 정의한) of메소드에 위 세개(유저정보 하나, 업체정보 두개)를 넣어서 어트리뷰트를 뽑아낸 후
        // -> 여기서 OAuthAttributes는 일종의 DTO라고 할 수 있겠지.
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        // 아래에 정의한 메소드를 가지고 가입 혹은 갱신을 시킨 다음에
        User user = saveOrUpdate(attributes);

        // 세션에다가 등록해주고
        httpSession.setAttribute("user", new SessionUser(user));

        // 유저를 뭔가 적절한 OAuth 오브젝트에 넣어서 리턴해주나보다.
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey()
        );
    }


    /***
     * 소셜로그인 유저 정보(attributes)를 가지고, 없는 유저면 가입시키고 있는 유저면 갱신시키는 메소드
     * @param attributes
     * @return 저장 완료된 유저 엔티티
     */
    private User saveOrUpdate(OAuthAttributes attributes) { // 소셜로그인 정보인 attributes는 email, picture, name을 담고 있다.
        User user = userRepository.findByEmail(attributes.getEmail()) // 이메일로 조회해서
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture())) // 있다면 해당 엔티티의 이름과 사진을 업데이트시켜주고
                .orElse(attributes.toEntity()); // 없다면 고대로 엔티티로 만들어서

        return userRepository.save(user); // 저-장
    }
}
