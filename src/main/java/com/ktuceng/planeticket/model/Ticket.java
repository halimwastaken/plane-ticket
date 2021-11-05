package com.ktuceng.planeticket.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Ticket {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
	
	private String name;
	
	boolean isSold;
	
	private Date time;
	
	private String seat;
	
	private String gate;
	
	@ManyToOne
	@JoinColumn(name="user_id", nullable=true)
	private UserInfo user;
	
	
	@ManyToOne
	@JoinColumn(name="plane_id", nullable=false)
	private Plane plane;


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public UserInfo getUser() {
		return user;
	}


	public void setUser(UserInfo user) {
		this.user = user;
	}


	public Plane getPlane() {
		return plane;
	}


	public void setPlane(Plane plane) {
		this.plane = plane;
	}


	public Date getTime() {
		return time;
	}


	public void setTime(Date time) {
		this.time = time;
	}


	public String getSeat() {
		return seat;
	}


	public void setSeat(String seat) {
		this.seat = seat;
	}


	public String getGate() {
		return gate;
	}


	public void setGate(String gate) {
		this.gate = gate;
	}


	public boolean isSold() {
		return isSold;
	}


	public void setSold(boolean isSold) {
		this.isSold = isSold;
	}
	
	
}
