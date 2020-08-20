package com.web.curation.dto.account;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    String email;
    String password;

}
