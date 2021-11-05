package com.ktuceng.planeticket.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ktuceng.planeticket.model.Plane;
import com.ktuceng.planeticket.model.Ticket;
import com.ktuceng.planeticket.model.UserInfo;


@Repository
@Transactional
public interface TicketRepository extends CrudRepository<Ticket, String>  {
	
	public Ticket findById(Integer id);
	
	public List<Ticket> findByUser(UserInfo user);
	public List<Ticket> findByPlane(Plane plane);
}