package com.ktuceng.planeticket.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.ktuceng.planeticket.model.Plane;
import com.ktuceng.planeticket.model.Ticket;
import com.ktuceng.planeticket.model.UserInfo;
import com.ktuceng.planeticket.repository.TicketRepository;

@Service
public class TicketService {

	@Autowired
	private TicketRepository repository;
	@Autowired
	private UserService userService;
	
	public List<Ticket> getAllTickets(){
		Iterable<Ticket> ticketIter = repository.findAll();
        List<Ticket> ticketList = new ArrayList<>();

        ticketIter.forEach(ticket-> {
            ticketList.add(ticket);
        });
        return ticketList;
	}
	
	public Ticket getTicketById(Integer id) {
		return repository.findById(id);
	}

	public List<Ticket> getTicketByUser(UserInfo user){
		return repository.findByUser(user);
	}
	public List<Ticket> getTicketByPlane(Plane plane){
		return repository.findByPlane(plane);
	}
	
	public Ticket addTicket(Ticket ticket) {
		return repository.save(ticket);
	}
	
	public Ticket updateTicket(Ticket ticket) {
		return repository.save(ticket);
	}
	
	public void deleteTicket(Ticket ticket) {
		repository.delete(ticket);
	}
	
	public Ticket buyTicket(Integer ticketId, String userEmail) {
		Ticket ticket = getTicketById(ticketId);
		UserInfo user = userService.getUserByEmail(userEmail);
		ticket.setUser(user);
		ticket.setSold(true);
		repository.save(ticket);
		return ticket;
	}
}


