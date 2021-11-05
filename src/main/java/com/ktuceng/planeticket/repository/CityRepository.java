package com.ktuceng.planeticket.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import com.ktuceng.planeticket.model.City;


@Repository
@Transactional
public interface CityRepository extends CrudRepository<City, String>  {
	
	public City findById(Integer id);
	
	
	
}