package com.ktuceng.planeticket.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktuceng.planeticket.model.Airport;

import com.ktuceng.planeticket.model.Plane;

import com.ktuceng.planeticket.repository.PlaneRepository;

@Service
public class PlaneService {
	
	@Autowired
	private PlaneRepository repository;
	
	public List<Plane> getAllPlanes(){
		Iterable<Plane> planeIter = repository.findAll();
        List<Plane> planeList = new ArrayList<>();
        
        planeIter.forEach(plane-> {
            planeList.add(plane);
        });
        return planeList;
	}

	
	public Plane getPlaneById(Integer id) {
		return repository.findById(id);
	}

	public List<Plane> getPlaneByAirport(Airport airport){
		return repository.findByAirport(airport);
	}
	public Plane addPlane(Plane plane) {
		return repository.save(plane);
	}
	
	public Plane updatePlane(Plane plane) {
		return repository.save(plane);
	}
	
	public void deletePlane(Plane plane) {
		repository.delete(plane);
	}
	
	
	
}
