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

import com.ktuceng.planeticket.model.Airport;
import com.ktuceng.planeticket.model.City;

import com.ktuceng.planeticket.service.AirportService;
import com.ktuceng.planeticket.service.CityService;



@RestController
public class AirportController {
	
	@Autowired
	private AirportService airportService;
	
	@Autowired
	private CityService cityService;
	
	@GetMapping("/getAllAirport")
	public Object getAllAirports(@RequestHeader HttpHeaders headers ) {
		
		List<Airport> airportList = airportService.getAllAirports();
		return new ResponseEntity<>(airportList, HttpStatus.OK);
	}
	
	@GetMapping("/getAirportById")
    public Object getAirportById(@RequestHeader HttpHeaders requestHeaders, @RequestParam Integer airportId) {
        Airport airport= airportService.getAirportById(airportId);
        if (airport== null) {
            return new ResponseEntity<>("Airport bulunamadi", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(airport, HttpStatus.OK);
    }
	
	@PostMapping("/deleteAirport")
    public Object deleteAirport(@RequestHeader HttpHeaders requestHeaders, @RequestParam Integer airportId) {
        Airport airport = airportService.getAirportById(airportId);
        try {
            airportService.deleteAirport(airport);
        } catch (Exception e) {
            e.printStackTrace();
           return new ResponseEntity<>("Airport silinemedi", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("", HttpStatus.OK);
    }
	
	@PostMapping("/addAirport")
	public Object addAirport(@RequestHeader HttpHeaders requestHeaders, @RequestBody Map<String,Object> body) {
		
		String name= (String) body.get("name");
		Integer cityId=(Integer) body.get("cityId");
		Airport airport = new Airport();
		City city= cityService.getCityById(cityId);
		
		if(city != null) {
			
			airport.setName(name);
			airport.setCity(city);
			airport = airportService.addAirport(airport);
			return new ResponseEntity<>(airport, HttpStatus.OK);
			
			
		}
		
		 return new ResponseEntity<>("city bulunamadı", HttpStatus.BAD_REQUEST);
	}
}
