package com.ktuceng.planeticket.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import com.ktuceng.planeticket.model.UserInfo;

@Repository
@Transactional
public interface UserRepository extends CrudRepository<UserInfo, String>  {
	
	public UserInfo findById(Integer id);
	
	public UserInfo findByEmail(String email);

	
}