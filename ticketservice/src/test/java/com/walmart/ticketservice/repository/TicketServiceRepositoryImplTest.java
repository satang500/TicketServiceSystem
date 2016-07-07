package com.walmart.ticketservice.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Optional;

import org.junit.BeforeClass;
import org.junit.Test;

import com.walmart.ticketservice.model.SeatHold;
import com.walmart.ticketservice.util.ConfigUtil;
import com.walmart.ticketservice.util.EmailUtil;
import com.walmart.ticketservice.util.Util;

/**
 * @author user
 *
 */
public class TicketServiceRepositoryImplTest extends ConfigUtil {

	static TicketServiceRepository ticketRepository;
	static EmailUtil emailUitl;
	static Util util;	
	
	@BeforeClass
	public static void init() {
		ticketRepository = new TicketServiceRepositoryImpl();
		emailUitl = new EmailUtil();
		util = new Util();	
	}
	
	/**
	 * Test method for {@link com.walmart.ticketservice.repository.TicketServiceRepositoryImpl#getNumberOfAvailableSeats(java.util.Optional)}.
	 */
	@Test
	public void testGetNumberOfAvailableSeats() {
		int numSeatsAvail0 = ConfigUtil.LEVEL_ROWS[0]*ConfigUtil.LEVEL_SEATS[0];
		int numSeatsAvailRes0 = ticketRepository.getNumberOfAvailableSeats(Optional.of(0));
		assertEquals(numSeatsAvail0, numSeatsAvailRes0);
	}

	/**
	 * Test method for {@link com.walmart.ticketservice.repository.TicketServiceRepositoryImpl#findAndHoldSeats(int, java.util.Optional, java.util.Optional, java.lang.String)}.
	 */
	@Test
	public void testFindAndHoldSeats() {
		SeatHold seatHold = ticketRepository.findAndHoldSeats(10, Optional.of(2), Optional.of(3), "motley.crue@google.com");
		assertNotNull(seatHold);
		assertEquals(seatHold.getCustomer().getCustEmail(), "motley.crue@google.com");
	}
}
