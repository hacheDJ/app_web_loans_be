package com.integrativeproyect.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignInRequest {
    private String userLogin;
    private String passwordLogin;
}
