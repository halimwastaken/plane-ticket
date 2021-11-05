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
import com.ktuceng.planeticket.model.Plane;

import com.ktuceng.planeticket.service.AirportService;
import com.ktuceng.planeticket.service.PlaneService;

@RestController
public class PlaneController {

	@Autowired
	private PlaneService planeService;
	
	@Autowired
	private AirportService airportService;
	
	@GetMapping("/getAllPlanes")
	public Object getAllPlanes(@RequestHeader HttpHeaders headers ) {
		
		List<Plane> planeList = planeService.getAllPlanes();
		return new ResponseEntity<>(planeList, HttpStatus.OK);
	}
	
	@GetMapping("/getplaneById")
    public Object getplaneById(@RequestHeader HttpHeaders requestHeaders, @RequestParam Integer planeId) {
        Plane plane= planeService.getPlaneById(planeId);
        if (plane== null) {
            return new ResponseEntity<>("plane bulunamadi", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(plane, HttpStatus.OK);
    }
	
	@PostMapping("/deletePlane")
    public Object deletePlane(@RequestHeader HttpHeaders requestHeaders, @RequestParam Integer planeId) {
        Plane plane = planeService.getPlaneById(planeId);
        try {
            planeService.deletePlane(plane);
        } catch (Exception e) {
            e.printStackTrace();
           return new ResponseEntity<>("Plane silinemedi", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("", HttpStatus.OK);
    }
	
	@PostMapping("/addPlane")
	public Object addPlane(@RequestHeader HttpHeaders requestHeaders, @RequestBody Map<String,Object> body) {
	
		String tailno= (String) body.get("tailno");
		String company= (String) body.get("company");
		Integer airportId=(Integer) body.get("airportId");
		Plane plane = new Plane();
		plane.setTailno(tailno);
		plane.setCompany(company);
		Airport airport = airportService.getAirportById(airportId);
		
		if(airport != null) {
			
			plane.setAirport(airport);
			plane = planeService.addPlane(plane);
			 return new ResponseEntity<>(plane, HttpStatus.OK);
		}
		 return new ResponseEntity<>("Plane bulunamadı", HttpStatus.BAD_REQUEST);
	
}
	}
