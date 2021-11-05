package com.ktuceng.planeticket.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.ktuceng.planeticket.model.UserInfo;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserService userInfoDAO;
	
	@Override
	public UserDetails loadUserByUsername(String userName) {
		UserInfo user = userInfoDAO.getUserByEmail(userName);
		GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole());
		return new User(user.getEmail(), user.getPassword(), Arrays.asList(authority));
	}
}