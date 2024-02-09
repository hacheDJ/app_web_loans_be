package com.integrativeproyect.entity;

import java.util.Date;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_user")
@Getter
@Setter
public class User{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
    private String namesUser;
    private String lastnameP;
    private String lastnameM;
    
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private Date registrationDate;
    
    private String address;
    private String cellphone;
    private String email;
    private String userLogin;
    private String passwordLogin;
    
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateBirth;
    
    private String numberDoc;
    
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "idDocType")
    @ManyToOne(fetch = FetchType.LAZY)
    private DocumentType idDocType;
    
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private Date updateDate;
    
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "idGroup")
    @ManyToOne(fetch = FetchType.LAZY)
    private Group idGroup;
    
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "idPortfolio")
    @ManyToOne(fetch = FetchType.LAZY)
    private PortfolioClient idPortfolio;
    
    private String role;

    

}