package com.ktuceng.planeticket.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ktuceng.planeticket.model.Airport;
import com.ktuceng.planeticket.model.Plane;



@Repository
@Transactional
public interface PlaneRepository extends CrudRepository<Plane, String>  {
	
	
	public Plane findById(Integer id);
	

	
	public List<Plane> findByAirport(Airport airport);
	
}