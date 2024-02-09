package com.integrativeproyect.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.integrativeproyect.entity.LoanCategory;
import com.integrativeproyect.entity.User;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RequestLoanRegisterReq {
	private long id;
    private String detail;
    private Double requestedAmount;
    

    private LocalDate loanStartDate;
    

    private LocalDate loanEndDate;
    
	private String state;
    private LocalDateTime registrationDate;
	private User idBorrower;
	private LoanCategory idLoanCat;
    private User idUserRegister;
    private User idUserUpdate;
    private int days;
}
