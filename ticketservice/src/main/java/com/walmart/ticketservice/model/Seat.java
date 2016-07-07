package com.walmart.ticketservice.model;

/**
 * @author user
 * Class for Seat Information
 */
public class Seat {
	private Level level;				// level of the seat
	private int row;					// row number of the seat
	private int seatNumber;				// seat number of the seat
	private SeatStatus seatStatus;		// seat status of the seat
	private Customer customer;			// seat holder of the seat
	
	public Seat(Level level, int row, int seatNumber) {
		this.level = level;
		this.row = row;
		this.seatNumber = seatNumber;
		this.seatStatus = SeatStatus.AVAILABLE;
	}

	public Level getLevel() {
		return this.level;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getSeatNumber() {			
		return seatNumber;
	}
	
	public boolean isAvailable() {
		if( seatStatus == SeatStatus.AVAILABLE )
			return true;
		else
			return false;
	}
	
	/**
	 * Hold the seat for customer
	 * @param customer
	 * @return
	 */
	public boolean holdSeat( Customer customer) {
		this.customer = customer;
		this.customer.addHoldSeatList(this);			// this.customer.parkInSpot(this);	
		this.seatStatus = SeatStatus.HOLD; 
		return true;
	}
	
	/**
	 * Researve the seat for customer
	 * @param customer
	 * @return
	 */
	public boolean reserveSeat(Customer customer ) {
		this.customer = customer;
		this.seatStatus = SeatStatus.RESERVED; 
		return true;
	}

	/**
	 * Remove customer from the seat, and make seat available
	 */
	public void removeCustomer() {					
		this.customer = null;
		this.seatStatus = SeatStatus.AVAILABLE;
	}
	
	public void setSeatStatus( SeatStatus seatStatus ) {
		this.seatStatus = seatStatus;
	}
	
	public String toString() {
		return "level = " + level.getLevel() + ", row = " + row + ", seatNumber = " + seatNumber + ", seatStatus = " + seatStatus;
	}

	public void print() {
		if (customer == null) {
			System.out.println(this);
		} 
		else {
			System.out.println( "row = " + row + ", seatNumber = " + seatNumber + ", seatStatus = " + seatStatus + ", cust email = " + customer.getCustEmail());
		}
	}
}
