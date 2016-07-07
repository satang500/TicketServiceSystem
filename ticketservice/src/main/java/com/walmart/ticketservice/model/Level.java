package com.walmart.ticketservice.model;

import java.util.ArrayList;
import java.util.List;


/**
 * @author user
 * Class for Level Information
 */
public class Level {
	private int levelId;
	private String levelName;
	private double levelPrice;
	private int rows;												// number of rows in each level
	private int seatsPerRow;										// number of seats in each row
	private Seat[] seats;				 
	private int availableSeats = 0; 								// number of available seats in this level
	private List<Customer> custList = new ArrayList<Customer>();	// customer lists who held seats in this level
	
	public int getLevel() {
		return levelId - 1;
	}
	
	public double getLevelPrice() {
		return this.levelPrice;
	}
	
	public Level(int levelId, String levelName, double levelPrice, int rows, int seatsPerRow ) {
		this.levelId = levelId;
		this.levelName = levelName;
		this.levelPrice = levelPrice;
		this.rows = rows;
		this.seatsPerRow = seatsPerRow;
		
		this.seats = new Seat[rows*seatsPerRow];
		
		for( int i = 0; i < rows*seatsPerRow; i++) {
			int curRow = i / seatsPerRow;
			seats[i] = new Seat(this, curRow, i);	    // seat(level, row, seatNumber)
		}
		availableSeats = rows*seatsPerRow;
	}
	
	public int getAvailableSeats() {					// not hold nor reserved
		return this.availableSeats;
	}
	
	public void addAvailableSeats( int addAvailableSeats ) {
		this.availableSeats += addAvailableSeats;
	}
	
	/**
	 * hold seats as many as you can for the given customer
	 * @param customer
	 * @param numSeatsNeeded
	 * @return
	 * return remaining requested seats
	 */
	public int holdSeatsForCustomer(Customer customer, int numSeatsNeeded) {
		int requestedSeats = numSeatsNeeded;
		
		for(int i = 0; i < seats.length && requestedSeats > 0 ; i++ ) {
			if( seats[i].isAvailable() ) {
				seats[i].holdSeat(customer);  	// hold a seat for the given customer
				availableSeats--;
				requestedSeats--;				// availableSeats
			}
		}
		setCustomerHoldSeatList(customer);
		return requestedSeats;			
	}
	
	public void setCustomerHoldSeatList( Customer customer ) {
		custList.add(customer);
	}

	public String toString() {
		return "LevelId = " + levelId + ", levelName = " + levelName + 
				", levelPrice = " + levelPrice + 
				", number of rows on this level = " + rows + ", number of seats per row = " + seatsPerRow + 
				", total number of seats = " +  (rows * seatsPerRow)  ;
	}
	
	public void print() {
		for (int i = 0; i < seats.length; i++) {
			Seat seat = seats[i];
			seat.print();
		}
	}
	
}
