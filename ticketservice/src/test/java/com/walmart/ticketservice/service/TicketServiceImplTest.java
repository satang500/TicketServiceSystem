package com.walmart.ticketservice.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Optional;

import org.junit.BeforeClass;
import org.junit.Test;

import com.walmart.ticketservice.model.SeatHold;
import com.walmart.ticketservice.util.ConfigUtil;

public class TicketServiceImplTest extends ConfigUtil {

	static TicketService ticketService;
	String seatHoldId = null;

	@BeforeClass
	public static void init() {
		ticketService = new TicketServiceImpl();
	}
	
	/**
	 * Test find the number of seats
	 */
	@Test
	public void testNumSeatsAvailable() {
		int numSeatsAvail1 = ConfigUtil.LEVEL_ROWS[1]*ConfigUtil.LEVEL_SEATS[1];
		int  numSeatsAvailRes1 = ticketService.numSeatsAvailable(Optional.of(1));
		assertEquals(numSeatsAvail1, numSeatsAvailRes1);	
	}

	/**
	 * Test find and hold seats
	 */
	@Test
	public void testFindAndHoldSeats() {
		SeatHold seatHold = ticketService.findAndHoldSeats(2, Optional.of(2), Optional.of(3), "star.wars@google.com");
	    
		if( seatHold != null )
	    System.out.println( "seat hold id = " + seatHold.getSeatHoldId() );
	    else
	    	System.out.println("null id");
		assertNotNull(seatHold);
		assertEquals(seatHold.getCustomer().getCustEmail(), "star.wars@google.com");
	}
}
