package com.ktuceng.planeticket.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ktuceng.planeticket.model.Airport;
import com.ktuceng.planeticket.model.City;



@Repository
@Transactional
public interface AirportRepository extends CrudRepository<Airport, String> {
	
	public Airport findById(Integer id);
	
	
	public List<Airport> findByCity(City city);
}