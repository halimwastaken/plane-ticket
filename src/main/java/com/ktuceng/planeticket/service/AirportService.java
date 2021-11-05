package com.ktuceng.planeticket.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktuceng.planeticket.model.Airport;
import com.ktuceng.planeticket.model.City;

import com.ktuceng.planeticket.repository.AirportRepository;

@Service
public class AirportService {

	@Autowired
	private AirportRepository repository;
	
	public List<Airport> getAllAirports(){
		Iterable<Airport> airportIter = repository.findAll();
        List<Airport> airportList = new ArrayList<>();

        airportIter.forEach(airport-> {
            airportList.add(airport);
        });
        return airportList;
	}
	
	public Airport getAirportById(Integer id) {
		return repository.findById(id);
	}
	
	public List<Airport> getAirportByCity(City city){
		return repository.findByCity(city);
	}
	
	public Airport addAirport(Airport airport) {
		return repository.save(airport);
	}
	
	public Airport updateAirport(Airport airport) {
		return repository.save(airport);
	}
	
	public void deleteAirport(Airport airport) {
		repository.delete(airport);
	}
}
