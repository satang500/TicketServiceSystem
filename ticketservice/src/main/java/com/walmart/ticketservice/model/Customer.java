package com.walmart.ticketservice.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author user
 * Class for Customer Information
 */
public class Customer {
	private int seatNeeded;
	private String fName;
	private String lName;
	private String custEmail;

	private List<Seat> holdSeatList;		// Hold list for customer  
	private List<Seat> reserveSeatList;		// Reserve list for customer 
	
	public Customer(int seatNeeded, String fName, String lName, String custEmail ) {
		this.seatNeeded = seatNeeded;
		this.fName = fName;
		this.lName = lName;
		this.custEmail = custEmail;
		
		this.holdSeatList = new ArrayList<Seat>();
		this.reserveSeatList = new ArrayList<Seat>();
	}
	
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public String getlName() {
		return lName;
	}
	public void setlName(String lName) {
		this.lName = lName;
	}
	public String getCustEmail() {
		return custEmail;
	}
	public void setCustEmail(String custEmail) {
		this.custEmail = custEmail;
	}
	
	public int getSeatNeeded() {
		return this.seatNeeded;
	}
	
	public void setSeatNeeded(int seatNeeded) {
		this.seatNeeded = this.seatNeeded - seatNeeded;
	}

	public void unAssignSeat() {
	}
	
	public boolean addHoldSeatList( Seat seat ) {
		this.holdSeatList.add(seat);
		return true;
	}
	
	public List<Seat> getHoldSeatList() {
		return this.holdSeatList;
	}

	public boolean addReserveSeatList( Seat seat ) {
		this.reserveSeatList.add(seat);
		return true;
	}
	
	public List<Seat>  getReserveSeatList() {
		return this.reserveSeatList;
	}

	public String toString() {
		return this.fName + ", " + this.lName + " requested " + this.seatNeeded + " numbers of seats."; 
	}
	
	public void print() {
		System.out.println(this);
	}
}
