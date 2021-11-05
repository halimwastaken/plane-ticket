package com.ktuceng.planeticket.controller;

import java.net.http.HttpHeaders;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.ktuceng.planeticket.model.City;

import com.ktuceng.planeticket.service.CityService;

@RestController
public class CityController {

	@Autowired
	private CityService cityService;
	
	@GetMapping("/getAllCity")
	public Object getAllCity(@RequestHeader HttpHeaders headers ) {
		
		List<City> cityList = cityService.getAllCitys();
		return new ResponseEntity<>(cityList, HttpStatus.OK);
	}

	@GetMapping("/getCityById")
    public Object getCityById(@RequestHeader HttpHeaders requestHeaders, @RequestParam Integer cityId) {
        City city= cityService.getCityById(cityId);
        if (city== null) {
            return new ResponseEntity<>("city bulunamadi", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(city, HttpStatus.OK);
    }
	
	@PostMapping("/deleteCity")
    public Object deleteCity(@RequestHeader HttpHeaders requestHeaders, @RequestParam Integer cityId) {
        City city = cityService.getCityById(cityId);
        try {
            cityService.deleteCity(city);
        } catch (Exception e) {
            e.printStackTrace();
           return new ResponseEntity<>("City silinemedi", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("", HttpStatus.OK);
    }
	
	@PostMapping("/addCity")
	public Object addCity(@RequestHeader HttpHeaders requestHeaders, @RequestBody Map<String,Object> body) {
		
		String name= (String) body.get("name");
		String postcode= (String) body.get("postcode");
		City city = new City();
		city.setPostcode(postcode);
		city.setName(name);
		
		
			
			city = cityService.addCity(city);
			return new ResponseEntity<>(city, HttpStatus.OK);
		
		
		

		
	}

}
	
