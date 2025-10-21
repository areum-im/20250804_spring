package com.example.api.security.service;

import com.example.api.entity.Members;
import com.example.api.entity.MembersRole;
import com.example.api.repository.MembersRepository;
import com.example.api.security.dto.MembersAuthDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class ClubOAuthUserDetailsService extends DefaultOAuth2UserService {
  private final MembersRepository membersRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    log.info("userRequest: " + userRequest);
    String clientName = userRequest.getClientRegistration().getClientName();
    log.info("clientName: " + clientName);
    OAuth2User oAuth2User = super.loadUser(userRequest);
    oAuth2User.getAttributes().forEach((k,v) -> log.info(k + ":" +v));

    String email = null;
    Members clubMember = null;
    if (clientName.equals("Google")) email = oAuth2User.getAttribute("email");

    // social 로그인 했을 때 이미 등록된건지 확인
    Optional<Members> result = MembersRepository.findByEmail(email);

    // 이미 등록 되었을 때
    if (result.isPresent()) {
      clubMember = result.get();
    } else { // 등록 안 되었을 때 DB 저장
      clubMember = Members.builder()
          .email(email)
          .name(email)
          .password(passwordEncoder.encode("1"))
          .fromSocial(true)
          .build();
      clubMember.addMemberRole(MembersRole.USER);
      MembersRepository.save(clubMember);
    }
    log.info("clubMember: " + clubMember);
    // session에 담기 위한 ClubAuthMemberDTO 생성
    MembersAuthDTO membersAuthDTO = new MembersAuthDTO(
        clubMember.getEmail(), clubMember.getPassword(),
        true,
        clubMember.getRoleSet().stream().map(role ->
          new SimpleGrantedAuthority("ROLE_" + role.name())
        ).collect(Collectors.toList()),
        oAuth2User.getAttributes()
    );
    membersAuthDTO.setFromSocial(clubMember.isFromSocial());
    membersAuthDTO.setName(clubMember.getName());
    return membersAuthDTO;
  }
}
