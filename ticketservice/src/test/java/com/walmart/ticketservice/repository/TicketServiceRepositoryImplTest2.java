/**
 * 
 */
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
public class TicketServiceRepositoryImplTest2 extends ConfigUtil {

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
	 * Test method for {@link com.walmart.ticketservice.repository.TicketServiceRepositoryImpl#reserveSeats(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testReserveSeats() {
		SeatHold seatHold = ticketRepository.findAndHoldSeats(10, Optional.of(2), Optional.of(3), "motley.crue@google.com");
		assertNotNull(seatHold);
		assertEquals(seatHold.getCustomer().getCustEmail(), "motley.crue@google.com");
		
		String reservationConfirmCode = ticketRepository.reserveSeats(seatHold.getSeatHoldId(), "star.wars@google.com");
		assertNotNull(reservationConfirmCode);
	}	
}
