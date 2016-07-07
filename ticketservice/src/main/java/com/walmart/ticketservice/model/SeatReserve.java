package com.walmart.ticketservice.model;

import com.walmart.ticketservice.util.Util;

/**
 * @author user
 * Class for SeatReserve
 */
public class SeatReserve {

	private String seatReserveConfirmationCode;
	private SeatHold seatHold;
	private String seatEmailContent;	
	private Customer customer;			// customer contains which level, row, seats are hold
	private Util util = new Util();
	
	public SeatReserve( Customer customer, SeatHold seatHold ) {
		this.seatReserveConfirmationCode = util.createId();
		this.seatHold = seatHold;
		this.customer = customer;
	}

	public SeatReserve( Customer customer, String seatEmailContent, SeatHold seatHold ) {
		this.seatReserveConfirmationCode = util.createId();
		this.seatHold = seatHold;
		this.seatEmailContent = seatEmailContent;
		this.customer = customer;
	}
	
	public String getSeatReserveConfirmationCode() {
		return seatReserveConfirmationCode;
	}

	public String getSeatEmailContent() {
		return seatEmailContent;
	}

	public void setSeatEmailContent(String seatEmailContent) {
		this.seatEmailContent = seatEmailContent;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String toString() {
		return "[SestReserve.toString] SEAT RESERVE: customer = " + customer.getCustEmail() + " held " + customer.getSeatNeeded() + " numbers of seats, " + 
				"seatReserveConfirmationCode = " + seatReserveConfirmationCode + 
				", seatEmailContent = " + getSeatEmailContent();
	}

	public void print() {
		System.out.println( this );
	}
	
}

