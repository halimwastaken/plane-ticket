package com.ktuceng.planeticket.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ktuceng.planeticket.model.UserInfo;
import com.ktuceng.planeticket.service.UserService;



@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Value("${oauth2.client.username}")
	private String oauth2User;
	
	@Value("${oauth2.client.password}")
	private String oauth2Password;

	@PostMapping("/register")
	public Object register(@RequestParam String email,@RequestParam String password,@RequestParam String name
            ,@RequestParam("file") MultipartFile file,@RequestParam String tcno, @RequestParam String phoneno) {
		UserInfo user = new UserInfo();
		
		try {
			byte[] image = file.getBytes();
			user.setImage(image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		user.setName(name);
		user.setTcno(tcno);
		user.setPhoneno(phoneno);
		user.setEmail(email);
		user.setPassword(new BCryptPasswordEncoder().encode(password));
		user.setRole("User");
		
		user = userService.addUser(user);
		
		if (user == null) {
			return new ResponseEntity<>("Kayit Basarisiz", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>("", HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public Object login( @RequestBody Map<String, Object> body) {
		String email = (String) body.get("email");
		String password = (String) body.get("password");
		
		UserInfo user = userService.getUserByEmailAndPassword(email, password);
		
		if (user != null) {
			Map<String, Object> result = new LinkedHashMap<String, Object>();
			
			result.put("role", user.getRole());
			result.put("oauthUsername", oauth2User);
			result.put("oauthPassword", oauth2Password);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Yanlis Email/Sifre", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/getUserDetails")
	public Object getUserDetails(@RequestHeader HttpHeaders requestHeaders, @RequestParam String userEmail) {
		UserInfo user = userService.getUserByEmail(userEmail);
		
		if (user != null) {
			Map<String, Object> result = new LinkedHashMap<String, Object>();
			
			result.put("name", user.getName());
			result.put("tcno", user.getTcno());
			result.put("phoneno", user.getPhoneno());
			
			result.put("email", user.getEmail());
			
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Yanlis Email/Sifre", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/getUserRole")
	public Object getUserRole(@RequestHeader HttpHeaders requestHeaders, @RequestParam String userEmail) {
		UserInfo user = userService.getUserByEmail(userEmail);
		
		if (user != null) {
			String role = user.getRole();
			return new ResponseEntity<>(role, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Yanlis Email/Sifre", HttpStatus.BAD_REQUEST);
		}
	}	
	
	@PostMapping("/updateUser")
	public Object updateUser(@RequestHeader HttpHeaders requestHeaders, @RequestParam String email, @RequestParam String name
            , @RequestParam("file") MultipartFile file, @RequestParam String tcno, @RequestParam String phoneno) {
	
		
		UserInfo user = userService.getUserByEmail(email);
		user = userService.updateUser(user, email, name, phoneno, tcno, file);
		
		if (user == null) {
			return new ResponseEntity<>("Guncelleme Basarisiz", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>("", HttpStatus.OK);
	}
	
	@PostMapping("/updateUserPassword")
	public Object updateUserPassword(@RequestHeader HttpHeaders requestHeaders, @RequestBody Map<String, Object> body) {
		String email = (String) body.get("email");
		String oldPassword = (String) body.get("oldPassword");
		String newPassword = (String) body.get("newPassword");
		
		UserInfo user = userService.getUserByEmail(email);
		
		user = userService.updateUserPassword(user, oldPassword, newPassword);
		
		if (user == null) {
			return new ResponseEntity<>("Guncelleme Basarisiz", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>("", HttpStatus.OK);
	}
}