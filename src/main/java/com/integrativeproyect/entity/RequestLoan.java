package com.integrativeproyect.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_request_loan")
@Getter
@Setter
public class RequestLoan {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
    private String detail;
    private Double requestedAmount;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate loanStartDate;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate loanEndDate;
    
	private String state;
	
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime registrationDate;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "idBorrower")
    @ManyToOne(fetch = FetchType.LAZY)
	private User idBorrower;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "idLoanCat")
    @ManyToOne(fetch = FetchType.LAZY)
	private LoanCategory idLoanCat;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "idUserRegister")
    @ManyToOne(fetch = FetchType.LAZY)
    private User idUserRegister;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "idUserUpdate")
    @ManyToOne(fetch = FetchType.LAZY)
    private User idUserUpdate;
}
