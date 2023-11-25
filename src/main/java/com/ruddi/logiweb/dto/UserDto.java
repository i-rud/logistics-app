package com.ruddi.logiweb.dto;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class UserDto {
    private String username;
    private String password;
    private String role;
    private List<GrantedAuthority> authorities = new ArrayList<>();
}
