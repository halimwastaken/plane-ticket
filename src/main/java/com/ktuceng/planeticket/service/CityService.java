package com.ktuceng.planeticket.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.ktuceng.planeticket.model.City;
import com.ktuceng.planeticket.repository.CityRepository;

@Service
public class CityService {

	@Autowired
	private CityRepository repository;
	
	public City getCityById(Integer id) {
		return repository.findById(id);
	}
	
	public List<City> getAllCitys(){
		Iterable<City> cityIter = repository.findAll();
        List<City> cityList = new ArrayList<>();

        cityIter.forEach(city-> {
            cityList.add(city);
        });
        return cityList;
	}
	public City addCity(City city) {
		return repository.save(city);
	}
	
	public City updatecity(City city) {
		return repository.save(city);
	}
	
	public void deleteCity(City city) {
		repository.delete(city);
	}
	
}
