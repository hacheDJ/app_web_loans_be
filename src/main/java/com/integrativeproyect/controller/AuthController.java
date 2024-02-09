package com.integrativeproyect.controller;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.integrativeproyect.dto.UserSignInRequest;
import com.integrativeproyect.dto.UserSingInRes;
import com.integrativeproyect.entity.User;
import com.integrativeproyect.repository.UserRepository;
import com.integrativeproyect.security.JwtHandler;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("api/v0.1/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
	
	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	private JwtHandler jwtHandler;
	
	@ResponseBody
	@GetMapping("/test")
	public ResponseEntity<?> test(){
		HashMap<String, String> res = new HashMap<>();
		res.put("msg","Ok!!!!!");
		return ResponseEntity.ok(res);
	}

	@ResponseBody
	@PostMapping
	public ResponseEntity<?> signIn(@RequestBody UserSignInRequest userReq){
		HashMap<String, Object> response = new HashMap<>();

		Optional<User> userOptionalReq = userRepository.findByUserLogin(userReq.getUserLogin());
		
		if(userOptionalReq.isEmpty()) {
			response.put("error", true);
			response.put("message", "Incorrect Username");
			response.put("user", null);
			response.put("token", null);
		}else {
			if(!passwordEncoder.matches(userReq.getPasswordLogin(), userOptionalReq.get().getPasswordLogin())) {
				response.put("error", true);
				response.put("message", "Incorrect password");
				response.put("user", null);
				response.put("token", null);
			}else {
				User objOut = userOptionalReq.get();
				UserSingInRes objUserRes = new UserSingInRes();

				objUserRes.setId(objOut.getId());
				objUserRes.setNamesUser(objOut.getNamesUser());
				objUserRes.setLastnameP(objOut.getLastnameP());
				objUserRes.setLastnameM(objOut.getLastnameM());
				objUserRes.setRegistrationDate(objOut.getRegistrationDate());
				objUserRes.setAddress(objOut.getAddress());
				objUserRes.setCellphone(objOut.getCellphone());
				objUserRes.setEmail(objOut.getEmail());
				objUserRes.setUserLogin(objOut.getUserLogin());
				objUserRes.setDateBirth(objOut.getDateBirth());
				objUserRes.setNumberDoc(objOut.getNumberDoc());
				objUserRes.setIdDocType(objOut.getIdDocType().getId());
				objUserRes.setUpdateDate(objOut.getUpdateDate());
				objUserRes.setIdGroup(objOut.getIdGroup().getId());
				objUserRes.setRole(objOut.getRole());
				
				response.put("error", false);
				response.put("message", "Welcome " + userOptionalReq.get().getNamesUser());
				response.put("user", objUserRes);
				response.put("token", jwtHandler.signToken(userOptionalReq.get()));
			}
			
			
			
		}
		return ResponseEntity.ok(response);
	}
	
}
