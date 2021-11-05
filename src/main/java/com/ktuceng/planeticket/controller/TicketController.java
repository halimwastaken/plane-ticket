package com.ktuceng.planeticket.controller;

import java.net.http.HttpHeaders;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.ktuceng.planeticket.model.Plane;
import com.ktuceng.planeticket.model.Ticket;
import com.ktuceng.planeticket.service.PlaneService;
import com.ktuceng.planeticket.service.TicketService;

@RestController
public class TicketController {

	
	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private PlaneService planeService;
	
	@GetMapping("/getAllTickets")
	public Object getAllTickets(@RequestHeader HttpHeaders headers ) {
		
		List<Ticket> ticketList = ticketService.getAllTickets();
		return new ResponseEntity<>(ticketList, HttpStatus.OK);
	}
	@GetMapping("/getticketById")
    public Object getticketById(@RequestHeader HttpHeaders requestHeaders, @RequestParam Integer ticketId) {
        Ticket ticket= ticketService.getTicketById(ticketId);
        if (ticket== null) {
            return new ResponseEntity<>("ticket bulunamadi", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(ticket, HttpStatus.OK);
    }
	
	@PostMapping("/deleteTicket")
    public Object deleteTicket(@RequestHeader HttpHeaders requestHeaders, @RequestParam Integer ticketId) {
        Ticket ticket = ticketService.getTicketById(ticketId);
        try {
            ticketService.deleteTicket(ticket);
        } catch (Exception e) {
            e.printStackTrace();
           return new ResponseEntity<>("Ticket silinemedi", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("", HttpStatus.OK);
    }
	
	@PostMapping("/addTicket")
	public Object addTicket(@RequestHeader HttpHeaders requestHeaders, @RequestBody Map<String,Object> body) {
		
		String name= (String) body.get("name");
		String time= (String) body.get("time");
		String seat= (String) body.get("seat");
		String gate= (String) body.get("gate");
		Integer planeId=(Integer) body.get("planeId");
		Ticket ticket = new Ticket();
		ticket.setName(name);
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = null;
		try {
			date = dt.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ticket.setGate(gate);
		ticket.setSeat(seat);
		ticket.setSold(false);
		Plane plane = planeService.getPlaneById(planeId);
		if(plane != null && date != null) {
			ticket.setTime(date);
			ticket.setPlane(plane);
			ticket = ticketService.addTicket(ticket);
			 return new ResponseEntity<>(ticket, HttpStatus.OK);
			
		}
		 return new ResponseEntity<>("Plane bulunamadı", HttpStatus.BAD_REQUEST);
		
		
	}
}
