package com.integrativeproyect.dto;

import java.util.Date;

import com.integrativeproyect.entity.DocumentType;
import com.integrativeproyect.entity.Group;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSingInRes {
	private Long id;
    private String namesUser;
    private String lastnameP;
    private String lastnameM;
    private Date registrationDate;
    private String address;
    private String cellphone;
    private String email;
    private String userLogin;
    private Date dateBirth;
    private String numberDoc;
    private int idDocType;
    private Date updateDate;
    private int idGroup;
    private String role;
}
