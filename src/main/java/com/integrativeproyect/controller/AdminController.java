package com.integrativeproyect.controller;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.integrativeproyect.dto.RequestLoanRegisterReq;
import com.integrativeproyect.dto.UserListByRoleBossAndLenderRes;
import com.integrativeproyect.dto.UserRegisterReq;
import com.integrativeproyect.dto.UserToEditRes;
import com.integrativeproyect.entity.BankAccount;
import com.integrativeproyect.entity.PortfolioClient;
import com.integrativeproyect.entity.RequestLoan;
import com.integrativeproyect.entity.User;
import com.integrativeproyect.repository.BankAccountRepository;
import com.integrativeproyect.repository.RequestLoanRepository;
import com.integrativeproyect.repository.UserRepository;
import com.integrativeproyect.util.Role;

import lombok.AllArgsConstructor;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v0.1/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {
	
	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	private RequestLoanRepository requestLoanRepository;
	private BankAccountRepository bankAccountRepository;
	
	@PostMapping
	@ResponseBody
	public ResponseEntity<?> registerLenderBossAndLender(@RequestBody UserRegisterReq userRegisterReq) throws Exception {
		HashMap<String, Object> res = new HashMap<>();
		User objUser = new User();
		
		PortfolioClient objPf = new PortfolioClient();
		objPf.setId(1);

		try {
			validator(userRegisterReq);
			
			objUser.setNamesUser(userRegisterReq.getNamesUser());
			objUser.setLastnameP(userRegisterReq.getLastnameP());
			objUser.setLastnameM(userRegisterReq.getLastnameM());
			objUser.setRegistrationDate(new Date());
			objUser.setAddress(userRegisterReq.getAddress());
			objUser.setCellphone(userRegisterReq.getCellphone());
			objUser.setEmail(userRegisterReq.getEmail());
			objUser.setUserLogin(userRegisterReq.getUserLogin());
			objUser.setPasswordLogin(passwordEncoder.encode(userRegisterReq.getPasswordLogin()));
			objUser.setDateBirth(userRegisterReq.getDateBirth());
			objUser.setNumberDoc(userRegisterReq.getNumberDoc());
			objUser.setIdDocType(userRegisterReq.getIdDocType());
			objUser.setUpdateDate(new Date());
			objUser.setIdGroup(userRegisterReq.getIdGroup());
			objUser.setIdPortfolio(objPf);
			objUser.setRole(userRegisterReq.getRole());
			
			User objOut = userRepository.save(objUser);
			
			if(objOut == null) {
				res.put("error", true);
				res.put("message", "Ocurrio un error al agregar al usuario.");
			}else {
				res.put("error", false);
				res.put("message", "Se agrego el Usuario con ID: "+objOut.getId());
			}
			
			return ResponseEntity.ok(res);
		} catch (Exception e) {
			res.put("error", true);
			res.put("message", e.getMessage());
			
			return ResponseEntity.ok(res);
		}
	}
	
	@PatchMapping
	@ResponseBody
	public ResponseEntity<?> updateLenderBossAndLender(@RequestBody UserRegisterReq userRegisterReq) {
		HashMap<String, Object> res = new HashMap<>();
		
		Optional<User> userOptional =  userRepository.findById(userRegisterReq.getId());
		
		try {
			validator(userRegisterReq);
			
			if(userOptional.isEmpty()) {
				res.put("error", true);
				res.put("message", "Usuario con ID: "+userOptional.get().getId()+" no existe");
			}else {
				userOptional.get().setNamesUser(userRegisterReq.getNamesUser() == null ? userOptional.get().getNamesUser() : userRegisterReq.getNamesUser());
				userOptional.get().setLastnameP(userRegisterReq.getLastnameP());
				userOptional.get().setLastnameM(userRegisterReq.getLastnameM());
				userOptional.get().setRegistrationDate(userRegisterReq.getRegistrationDate() == null ? userOptional.get().getRegistrationDate() : userOptional.get().getRegistrationDate());
				userOptional.get().setAddress(userRegisterReq.getAddress());
				userOptional.get().setCellphone(userRegisterReq.getCellphone());
				userOptional.get().setEmail(userRegisterReq.getEmail());
				userOptional.get().setUserLogin(userRegisterReq.getUserLogin());
				userOptional.get().setPasswordLogin(userRegisterReq.getPasswordLogin() == null ? userOptional.get().getPasswordLogin() : passwordEncoder.encode(userRegisterReq.getPasswordLogin()));
				userOptional.get().setDateBirth(userRegisterReq.getDateBirth());
				userOptional.get().setNumberDoc(userRegisterReq.getNumberDoc());
				userOptional.get().setIdDocType(userRegisterReq.getIdDocType());
				userOptional.get().setUpdateDate(new Date());
				userOptional.get().setIdGroup(userRegisterReq.getIdGroup());
				userOptional.get().setIdPortfolio(userOptional.get().getIdPortfolio());
				userOptional.get().setRole(userRegisterReq.getRole().equals(userOptional.get().getRole()) ? userOptional.get().getRole() : userRegisterReq.getRole());
				
				User objOut = userRepository.save(userOptional.get());
				
				res.put("error", false);
				res.put("message", "Se actualizo al Usuario con ID: "+objOut.getId());
			}
			
			return ResponseEntity.ok(res);
			
		} catch (Exception e) {
			res.put("error", true);
			res.put("message", e.getMessage());
			
			return ResponseEntity.ok(res);
		}
	}
	
	@GetMapping("/listUserByRole")
	@ResponseBody
	public ResponseEntity<?> listByRoleLenderBossAndLender(){
		HashMap<String, Object> res = new HashMap<>();
		
		List<User> objOut = userRepository.findByRoleLenderBossAndLender();
		
		List<UserListByRoleBossAndLenderRes> result = objOut.stream().map( item -> {
			UserListByRoleBossAndLenderRes objUserList = new UserListByRoleBossAndLenderRes();
			objUserList.setId(item.getId());
			objUserList.setNameComplete(item.getNamesUser()+" "+item.getLastnameP()+" "+item.getLastnameM());
			objUserList.setRole(item.getRole());
			return objUserList;
			}).collect(Collectors.toList());
				
		res.put("lstUserRoleLenderBossAndLender", objOut);
		
		return ResponseEntity.ok(result);
	}
	
	@PostMapping("/findUserToEdit")
	@ResponseBody
	public ResponseEntity<?> findUserToEdit(@RequestBody UserRegisterReq userRegisterReq){
		HashMap<String, Object> res = new HashMap<>();
		UserToEditRes userToEditRes = new UserToEditRes();
		//System.out.println("---> USER ID"+userRegisterReq.getId());
		Optional<User> userOptional =  userRepository.findById(userRegisterReq.getId());
		
		if(userOptional.isEmpty()) {
			res.put("error", true);
			res.put("message", "Usuario con ID: "+userOptional.get().getId()+" no existe");
			res.put("userToEditRes", null);
		}else {
			
			
			userToEditRes.setNamesUser(userOptional.get().getNamesUser());
			userToEditRes.setLastnameP(userOptional.get().getLastnameP());
			userToEditRes.setLastnameM(userOptional.get().getLastnameM());
			userToEditRes.setAddress(userOptional.get().getAddress());
			userToEditRes.setCellphone(userOptional.get().getCellphone());
			userToEditRes.setEmail(userOptional.get().getEmail());
			userToEditRes.setUserLogin(userOptional.get().getUserLogin());
			userToEditRes.setDateBirth(userOptional.get().getDateBirth());
			userToEditRes.setNumberDoc(userOptional.get().getNumberDoc());
			userToEditRes.setIdDocType(userOptional.get().getIdDocType().getId());
			userToEditRes.setIdGroup(userOptional.get().getIdGroup().getId());
			userToEditRes.setRole(userOptional.get().getRole());
			
			res.put("error", false);
			res.put("message", "ok");
			res.put("userToEditRes", userToEditRes);
		}	
		
		return ResponseEntity.ok(res);	
	}
	
	@PostMapping("/listUserByRoleForUpdateLender")
	@ResponseBody
	public ResponseEntity<?> listUserByRoleForUpdateLender(@RequestBody User user){
		HashMap<String, Object> res = new HashMap<>();
		
		List<User> objOut = userRepository.findByRoleLenderAndByGroup(user.getIdGroup().getId());
		
		List<UserListByRoleBossAndLenderRes> result = objOut.stream().map( item -> {
			UserListByRoleBossAndLenderRes objUserList = new UserListByRoleBossAndLenderRes();
			objUserList.setId(item.getId());
			objUserList.setNameComplete(item.getNamesUser()+" "+item.getLastnameP()+" "+item.getLastnameM());
			objUserList.setRole(item.getRole());
			return objUserList;
			}).collect(Collectors.toList());
				
		res.put("listUserByRoleForUpdateLender", objOut);
		
		return ResponseEntity.ok(result);
	}
	
	@PostMapping("/registerRequestLoanByBorrower")
	@ResponseBody
	public ResponseEntity<?> registerRequestLoanByBorrower(@RequestBody RequestLoanRegisterReq objRLRegisterReq){
		HashMap<String, Object> res = new HashMap<>();
		RequestLoan objRL = new RequestLoan();
		
		int number = requestLoanRepository.maximunRequestToday(objRLRegisterReq.getIdBorrower().getId());
		
		if(number >= 2) {
			res.put("error", true);
			res.put("message", "No puedes registrar más de 2 solicitudes por día");
			return ResponseEntity.ok(res);
		}
		
		objRL.setDetail(objRLRegisterReq.getDetail());
		objRL.setRequestedAmount(objRLRegisterReq.getRequestedAmount());
		objRL.setLoanStartDate(objRLRegisterReq.getLoanStartDate());
		objRL.setLoanEndDate(objRLRegisterReq.getLoanStartDate().plusDays(objRLRegisterReq.getDays()));
		objRL.setState("PENDING");
		objRL.setRegistrationDate(LocalDateTime.now());
		objRL.setIdLoanCat(objRLRegisterReq.getIdLoanCat());
		objRL.setIdBorrower(objRLRegisterReq.getIdBorrower());
		objRL.setIdUserRegister(objRLRegisterReq.getIdUserRegister());
		objRL.setIdUserUpdate(objRLRegisterReq.getIdUserUpdate());
			
		RequestLoan resAddRl = requestLoanRepository.save(objRL);
		
		if(resAddRl == null) {
			res.put("error", true);
			res.put("message", "Ocurrio un error al agregar la Solicitud");
		} else{
			res.put("error", false);
			res.put("message", "Se registro la solicitud con número " + resAddRl.getId());
		}
		
		return ResponseEntity.ok(res);
	}
	
	@PostMapping("/registerBankAccountByBorrower")
	@ResponseBody
	public ResponseEntity<?> registerBankAccountByBorrower(@RequestBody BankAccount objBankAccount){
		HashMap<String, Object> res = new HashMap<>();
		
		List<BankAccount> exists = bankAccountRepository.findByNumberAccount(objBankAccount.getNumberAccount());
		
		if(objBankAccount.getBankName().equals("") || objBankAccount.getNumberAccount().equals("")) {
			res.put("error", true);
			res.put("message", "Campos vacíos");
			return ResponseEntity.ok(res);
		}
		
		if(!exists.isEmpty()) {
			res.put("error", true);
			res.put("message", "En número de cuenta ya existe");
			return ResponseEntity.ok(res);
		}

		BankAccount resSave = bankAccountRepository.save(objBankAccount);
		
		if(resSave == null) {
			res.put("error", true);
			res.put("message", "Ocurrio un error al agregar la Solicitud");
		}else {
			res.put("error", false);
			res.put("message", "Se registro la Cuenta Bancaria con número " + resSave.getId());
		}
		
		return ResponseEntity.ok(res);
	}
	
	@GetMapping("/listAllBankAccount")
	@ResponseBody
	public ResponseEntity<?> listAllBankAccount(){
		List<BankAccount> lstBankAccount = bankAccountRepository.findAll();
		
		return ResponseEntity.ok(lstBankAccount);
	}
	
	@GetMapping("/findBankAccountById/{id}")
	@ResponseBody
	public ResponseEntity<?> findBankAccountById(@PathVariable("id") int id){
		Optional<BankAccount> optionalBA = bankAccountRepository.findById(id);
		
		if(optionalBA.isEmpty()) {
			return ResponseEntity.ok("not found");
		}
		
		return ResponseEntity.ok(optionalBA);
	}
	
	private void validator(UserRegisterReq objValidate) throws Exception {
		if(objValidate.getId() == null) {//new user
			if(userRepository.findByUserLogin(objValidate.getUserLogin()).isPresent())
				throw new Exception("Username ya existe");
			
			if(userRepository.findByNumberDoc(objValidate.getNumberDoc()).isPresent())
				throw new Exception("El número de DNI ya esta registrado");
			
			if(!objValidate.getPasswordLogin().equals(objValidate.getConfirmPasswordLogin()))
				throw new Exception("Contraseñas no coinciden");
			
			if(objValidate.getIdGroup().getId() != 1) {//only teams,not FREE
				if(userRepository.findQuantityLenderBossInGroup(objValidate.getIdGroup().getId()) == 1 && objValidate.getRole().equals(Role.LENDER_BOSS))
					throw new Exception("El Grupo ya tiene un jefe asignado");
			}
		}else {//edit user
			if(objValidate.getId() == -1)
				throw new Exception("Selecciona un Usuario");
			
			if(!userRepository.findById(objValidate.getId()).get().getUserLogin().equals(objValidate.getUserLogin()))
				if(userRepository.findByUserLogin(objValidate.getUserLogin()).isPresent())
					throw new Exception("Username ya existe");
			
			if(!userRepository.findById(objValidate.getId()).get().getNumberDoc().equals(objValidate.getNumberDoc()))
				if(userRepository.findByNumberDoc(objValidate.getNumberDoc()).isPresent())
					throw new Exception("El número de DNI ya esta registrado");
			
			if(objValidate.getPasswordLogin() != null)
				if(!objValidate.getPasswordLogin().equals(objValidate.getConfirmPasswordLogin()))
					throw new Exception("Contraseñas no coinciden");
			
			
			
			if(userRepository.findById(objValidate.getId()).get().getRole().equals(Role.LENDER_BOSS) && userRepository.findById(objValidate.getId()).get().getIdGroup().getId() != objValidate.getIdGroup().getId())
				if(objValidate.getIdGroup().getId() != 1) {//only teams,not FREE
					if(userRepository.findQuantityLenderBossInGroup(objValidate.getIdGroup().getId()) == 1 && objValidate.getRole().equals(Role.LENDER_BOSS))
						throw new Exception("El Grupo ya tiene un jefe asignado");
				}
			
			if(userRepository.findById(objValidate.getId()).get().getRole().equals(Role.LENDER) && !objValidate.getRole().equals(userRepository.findById(objValidate.getId()).get().getRole()))
				if(objValidate.getIdGroup().getId() != 1) {//only teams,not FREE
					if(userRepository.findQuantityLenderBossInGroup(objValidate.getIdGroup().getId()) == 1 && objValidate.getRole().equals(Role.LENDER_BOSS))
						throw new Exception("El Grupo ya tiene un jefe asignado");
				}
			
			
			
		}//all users
		
		
		if(objValidate.getIdDocType().getId() == -1)
			throw new Exception("Debes seleccionar Tipo de Documento");
		
		if(objValidate.getIdGroup().getId() == -1)
			throw new Exception("Debes seleccionar un Grupo");
		
		if(objValidate.getRole().equals("-1"))
			throw new Exception("Debes seleccionar un Rol");
		
		
		
		/*if(!objValidate.getRole().equals(Role.LENDER_BOSS) || !objValidate.getRole().equals(Role.LENDER) || !objValidate.getRole().equals(Role.BORROWER))
			throw new Exception("Rol prohibido");*/
		
		if(objValidate.getRole().equals(Role.ADMIN))
			throw new Exception("No se puede asignar este Rol");
		
	}
	
	
}