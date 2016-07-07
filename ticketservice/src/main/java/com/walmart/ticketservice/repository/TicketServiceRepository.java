package com.walmart.ticketservice.repository;

import java.util.Optional;
import java.util.Set;

import com.walmart.ticketservice.model.SeatHold;

public interface TicketServiceRepository {

	/**
	* The number of seats in the requested level that are neither held nor reserved
	*
	* @param venueLevel an Integer level to limit the search
	* @return the number of tickets available on the provided level
	*/
	int getNumberOfAvailableSeats(Optional<Integer> level);

	/**
	* Find and hold the best available seats for a customer
	*
	* @param numSeats the number of seats to find and hold. Mandatory param and should be numSeats > 0 
	* @param minLevel the minimum venue level
	* @param maxLevel the maximum venue level
	* @param customerEmail unique identifier for the customer. Mandatory param.
	* @return a SeatHold object identifying the specific seats and related information
	*/
	SeatHold findAndHoldSeats(int numSeats, Optional<Integer> minLevel, Optional<Integer> maxLevel, String customerEmail);
	
	/**
	* Commit seats held for a specific customer
	*
	* @param seatHoldId the seat hold identifier
	* @param customerEmail the email address of the customer to which the seat hold is assigned
	* @return a reservation confirmation code
	*/
	String reserveSeats(String seatHoldId, String customerEmail);

	/**
	 * Displays the current status in the ticket system
	 * void
	 */
	void viewTicketSystem();
	
	/**
	 *  
	 * @return
	 * Set<String> a set of held seat identifiers
	 *
	 */
	 Set<String> getSeatHoldIdSet();
}
