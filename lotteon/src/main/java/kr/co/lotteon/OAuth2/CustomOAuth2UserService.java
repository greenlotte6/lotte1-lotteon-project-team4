package kr.co.lotteon.OAuth2;

import kr.co.lotteon.dto.OAuth2.*;
import kr.co.lotteon.entity.OAuth2;
import kr.co.lotteon.repository.OAuth2Repository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final OAuth2Repository oAuth2Repository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("getAttributes : {}", oAuth2User.getAttributes());

        // 네이버인지 구글인 카카오인지 확인하기 위한 값
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        OAuth2Response oAuth2Response = null;
        // 뒤에 진행할 다른 소셜 서비스 로그인을 위해 구분 => 구글
        if (registrationId.equals("google")) {
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());

        } else if (registrationId.equals("naver")) {
            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());

        } else if (registrationId.equals("kakao")) {
            oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());

        } else {
            return null;
        }

        log.info("registrationId : {}", registrationId);

        //리소스 서버에서 발급 받은 정보로 사용자를 특정할 아이디값을 만듦
        String name = oAuth2Response.getProvider() + "" + oAuth2Response.getProviderId();
        OAuth2 existData = oAuth2Repository.findByName(name);

        if (existData == null) {

            // 새로운 OAuth2 데이터 생성
            OAuth2 oAuth2 = new OAuth2();
            oAuth2.setUsername(oAuth2Response.getUsername()); // 실제 사용자 이름
            oAuth2.setEmail(oAuth2Response.getEmail());
            oAuth2.setName(name);  // 고유 ID
            oAuth2.setRole("USER");

            oAuth2Repository.save(oAuth2);

            // OAuth2DTO 생성 및 반환
            OAuth2DTO oAuth2DTO = new OAuth2DTO();
            oAuth2DTO.setUsername(oAuth2.getUsername());  // 실제 사용자 이름
            oAuth2DTO.setEmail(oAuth2.getEmail());
            oAuth2DTO.setName(name);  // 고유 ID
            oAuth2DTO.setRole("USER");

            return new CustomOAuth2User(oAuth2DTO);

        } else {

            // 기존 데이터 업데이트
            existData.setEmail(oAuth2Response.getEmail());
            existData.setUsername(oAuth2Response.getUsername());  // username 갱신

            oAuth2Repository.save(existData);

            // 공통된 리턴값 생성
            OAuth2DTO oAuth2DTO = new OAuth2DTO();
            oAuth2DTO.setUsername(existData.getUsername());
            oAuth2DTO.setEmail(existData.getEmail());
            oAuth2DTO.setName(name);
            oAuth2DTO.setRole("USER");

            return new CustomOAuth2User(oAuth2DTO);
        }
    }
}