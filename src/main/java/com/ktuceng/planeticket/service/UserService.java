package com.ktuceng.planeticket.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ktuceng.planeticket.model.UserInfo;
import com.ktuceng.planeticket.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	
	public UserInfo getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	public UserInfo getUserByEmailAndPassword(String email, String password) {
		UserInfo User = getUserByEmail(email);
		
		if (User != null) {
			if (new BCryptPasswordEncoder().matches(password, User.getPassword())) {
				return User;
			}
		}
		return null;
	}
	
	public UserInfo addUser(UserInfo User) {
		return userRepository.save(User);
	}
	
	public UserInfo updateUser(UserInfo User, String email, String name,  String phone,   String tcno, MultipartFile file ) {
		User.setEmail(email);
		User.setName(name);
		try {
			
			byte[] image = file.getBytes();
			User.setImage(image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		User.setPhoneno(phone);
		
		
		
		User.setTcno(tcno);
		return userRepository.save(User);
	}
	
	public UserInfo updateUserPassword(UserInfo User, String oldPassword, String newPassword) {
		if (User != null) {
			if (new BCryptPasswordEncoder().matches(oldPassword, User.getPassword())) {
				User.setPassword(new BCryptPasswordEncoder().encode(newPassword));
				return userRepository.save(User);
			}
		}
		return null;
	}
}
