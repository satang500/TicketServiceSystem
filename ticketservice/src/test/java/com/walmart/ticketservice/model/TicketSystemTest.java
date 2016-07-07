package com.walmart.ticketservice.model;

import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.walmart.ticketservice.util.ConfigUtil;

public class TicketSystemTest extends ConfigUtil {
	static TicketSystem ticketSystem;
	static int MIN_LEVEL;
	static int MAX_LEVEL;

	@BeforeClass
	public static void init() {
		ticketSystem = new TicketSystem();
		MIN_LEVEL = ConfigUtil.MIN_LEVEL;
		MAX_LEVEL = ConfigUtil.MAX_LEVEL;
	}
	
	/**
	 * Test method for {@link com.walmart.ticketservice.model.TicketSystem#numSeatAavailable(int, int)}.
	 */
	@Test
	public void testNumSeatAavailable() {
		int numSeatsAvail12 = 	ConfigUtil.LEVEL_ROWS[MIN_LEVEL]*ConfigUtil.LEVEL_SEATS[MIN_LEVEL] +
								ConfigUtil.LEVEL_ROWS[MIN_LEVEL+1]*ConfigUtil.LEVEL_SEATS[MIN_LEVEL+1];
		
		int  numSeatsAvailRes12 = ticketSystem.numSeatAavailable(MIN_LEVEL, MIN_LEVEL+1);
		Assert.assertEquals(numSeatsAvail12, numSeatsAvailRes12);	
	}

	/**
	 * Test method for {@link com.walmart.ticketservice.model.TicketSystem#holdSeats(com.walmart.ticketservice.model.Customer, int, int, int)}.
	 */
	@Test
	public void testHoldSeats() {
		int seatNeeded = 7;
		
		// Hold 7 seats between level0 and level1 ( level id is index )  
		Customer customer = ticketSystem.holdSeats( new Customer(seatNeeded, "Vince", "Neil", "vince.neil@gmal.com"), seatNeeded, MAX_LEVEL-1, MAX_LEVEL );
		List<Seat> holdSeatList = customer.getHoldSeatList();
		Assert.assertEquals(7, holdSeatList.size());
	}
}
