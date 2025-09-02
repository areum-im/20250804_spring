package com.example.ex7.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Log4j2
@Getter
@Setter
@ToString
// security 전용, session을 생성할 때 User를 상속받아야 Authentication 객체에 저장
public class ClubAuthMemberDTO extends User {

  private String email;
  private String name;
  private boolean fromSocial;

  public ClubAuthMemberDTO(String username, String password, boolean fromSocial,
                           Collection<? extends GrantedAuthority> authorities) {
    super(username, password, authorities);
    email = username;
    this.fromSocial = fromSocial;
  }
}